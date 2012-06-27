package com.viva.mypad;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.viva.mypad.Adapter.DBAdapter;
import com.viva.mypad.Adapter.MemoArrayAdapter;
import com.viva.mypad.Item.MemoItem;

public class MyMemoPadActivity extends Activity
{
    private final String TAG = "MYMEMOPAD-----------";
    private final int INSERT_OK = 0;
    private DBAdapter mDbAdapter;
    private ListView mMemoListView;
    private ArrayList<MemoItem> mMemoList;
    private MemoArrayAdapter mMemoListAdapter;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.e(TAG, "onCreate");

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(this.getResources().getString(R.string.actionbar_title));

        mDbAdapter = new DBAdapter(this);
        mDbAdapter.open();

        mMemoListView = (ListView)findViewById(R.id.note_list_view);
        TextView emptyView = (TextView)findViewById(R.id.empty);

        mMemoList = loadData();
        if(mMemoList == null)
        {
            mMemoListView.setEmptyView(emptyView);
        }
        else
        {
            mMemoListAdapter = new MemoArrayAdapter(this, mMemoList);
            mMemoListView.setAdapter(mMemoListAdapter);
            emptyView.setVisibility(View.GONE);
        }
    }

    public void onResume()
    {
        super.onResume();
        Log.e(TAG, "ONRESUME");
    }

    public ArrayList<MemoItem> loadData()
    {
        
        Cursor cursor = mDbAdapter.getAllMemo();
        ArrayList<MemoItem> memoList = new ArrayList<MemoItem>();

        cursor.moveToFirst();
        while(cursor.moveToNext())
        {
            memoList.add(new MemoItem(cursor.getString(1), cursor.getString(3)));
        }

        return memoList;
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_add:
                startActivityForResult(new Intent(this, WriteMemoActivity.class), INSERT_OK);
            break;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode)
        {
            case INSERT_OK:
            	Log.e(TAG, "INSERT_OK");
            break;
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        mDbAdapter.close();
    }
}