package com.viva.mypad;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viva.mypad.Adapter.DBAdapter;

public class ViewMemoActivity extends SherlockActivity implements OnClickListener
{
    private long mMemoId;
    private DBAdapter mDbAdapter;
    private boolean mIsImportant;
    private Button mViewImportant;
    private String mMemoTitle;
    private String mMemoContent;

    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.view_memo);

        mIsImportant = false;
        mDbAdapter = new DBAdapter(this);
        mDbAdapter.open();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mMemoId = getIntent().getExtras().getLong("memoid");

        TextView dateView = (TextView)findViewById(R.id.dateView);
        TextView memoView = (TextView)findViewById(R.id.memoView);
        mViewImportant = (Button)findViewById(R.id.viewImportant);

        Cursor cursor = mDbAdapter.getMemo(mMemoId);
        mMemoTitle = cursor.getString(1);
        mMemoContent = cursor.getString(2);

        if(mMemoTitle.equals(""))
        {
            actionBar.setTitle(getResources().getString(R.string.no_title));
        }
        else
        {
            actionBar.setTitle(mMemoTitle);
        }

        dateView.setText(cursor.getString(3));
        memoView.setText(mMemoContent);

        if(cursor.getInt(4) == 1)
        {
            mIsImportant = true;
            mViewImportant.setBackgroundResource(R.drawable.ics_rate_important);
        }

        mViewImportant.setOnClickListener(this);
        cursor.close();
    }

    public void onClick(View v)
    {
        if(mIsImportant)
        {
            mIsImportant = false;
            v.setBackgroundResource(R.drawable.ics_not_important);
            mDbAdapter.updateImportant(mMemoId, 0);
        }
        else
        {
            mIsImportant = true;
            v.setBackgroundResource(R.drawable.ics_rate_important);
            mDbAdapter.updateImportant(mMemoId, 1);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getSupportMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = null;
        switch(item.getItemId())
        {
            case android.R.id.home:
                intent = new Intent(this, MyMemoPadActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
            break;

            case R.id.menu_edit:
                intent = new Intent(this, WriteMemoActivity.class);
                intent.putExtra("editMode", 1);
                intent.putExtra("memoid", mMemoId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
            break;

            case R.id.menu_delete:
                mDbAdapter.deleteMemo(mMemoId);
                intent = new Intent(ViewMemoActivity.this, MyMemoPadActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            break;

            case R.id.menu_share:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_title) + " " + mMemoTitle);
                intent.putExtra(Intent.EXTRA_TEXT, mMemoContent + " " + getResources().getString(R.string.share_message));
                startActivity(intent);
            break;
        }
        return true;
    }

    protected void onDestroy()
    {
        super.onDestroy();
        mDbAdapter.close();
    }
}
