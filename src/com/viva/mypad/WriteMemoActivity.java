package com.viva.mypad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.viva.mypad.Adapter.DBAdapter;
import com.viva.mypad.Util.Util;

public class WriteMemoActivity extends Activity
{
    private String mNow;
    private TextView mDateView;
    private EditText mEditTitle, mEditContent;
    private DBAdapter mDbAdapter;
    private int mIsEditMode;
    private long mMemoId;

    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.write_memo);

        mDbAdapter = new DBAdapter(this);
        mDbAdapter.open();

        mDateView = (TextView)findViewById(R.id.dateView);
        mEditTitle = (EditText)findViewById(R.id.editTitle);
        mEditContent = (EditText)findViewById(R.id.editContent);

        mIsEditMode = getIntent().getIntExtra("editMode", 0);
        if(mIsEditMode == 1)
        {
            // edit mode
            mMemoId = getIntent().getLongExtra("memoid", 0);
            Cursor cursor = mDbAdapter.getMemo(mMemoId);
            mEditTitle.setText(cursor.getString(1));
            mEditContent.setText(cursor.getString(2));
        }

        mNow = Util.getNowDateTime();
        mDateView.setText(mNow);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.write_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_save:
                if(mIsEditMode == 1)
                {
                    mDbAdapter.updateMemo(mMemoId, mEditTitle.getText().toString(), mEditContent.getText().toString(), 0);
                }
                else
                {
                    mMemoId = mDbAdapter.insertMemo(mEditTitle.getText().toString(), mEditContent.getText().toString(), mNow, 0);
                }

                //this.setResult(RESULT_OK, null);
                mDbAdapter.close();
                Intent intent = new Intent(this, ViewMemoActivity.class);
                intent.putExtra("memoid", mMemoId);
                startActivity(intent);
                this.finish();
            break;
        }
        return true;
    }
}
