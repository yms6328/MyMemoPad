package com.viva.mypad;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.viva.mypad.Adapter.DBAdapter;

public class ViewMemoActivity extends Activity
{
    private long mMemoId;
    private DBAdapter mDbAdapter;

    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.view_memo);
        mDbAdapter = new DBAdapter(this);
        mDbAdapter.open();

        ActionBar actionBar = getActionBar();
        mMemoId = getIntent().getExtras().getLong("memoid");

        TextView dateView = (TextView)findViewById(R.id.dateView);
        TextView memoView = (TextView)findViewById(R.id.memoView);

        Cursor cursor = mDbAdapter.getMemo(mMemoId);
        if(cursor.getString(1).equals(""))
        {
            actionBar.setTitle(getResources().getString(R.string.no_title));
        }
        else
        {
            actionBar.setTitle(cursor.getString(1));
        }

        dateView.setText(cursor.getString(3));
        memoView.setText(cursor.getString(2));
    }
}
