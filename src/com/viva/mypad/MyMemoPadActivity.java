package com.viva.mypad;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.viva.mypad.Adapter.DBAdapter;
import com.viva.mypad.Adapter.MemoArrayAdapter;
import com.viva.mypad.Item.MemoItem;

public class MyMemoPadActivity extends Activity implements OnItemClickListener
{
    private String TAG = "MEMOPAD";
    private final int UPDATE_MESSAGE = 2;
    private final int INSERT_OK = 0;
    private DBAdapter mDbAdapter;
    private ListView mMemoListView;
    private ArrayList<MemoItem> mMemoList;
    private MemoArrayAdapter mMemoListAdapter;
    private TextView mEmptyView;
    private boolean mIsChecked = false;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(this.getResources().getString(R.string.actionbar_title));

        mDbAdapter = new DBAdapter(this);
        mDbAdapter.open();

        mMemoList = new ArrayList<MemoItem>();
        mMemoListView = (ListView)findViewById(R.id.note_list_view);
        mMemoListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mMemoListAdapter = new MemoArrayAdapter(this, mMemoList, mDbAdapter);
        mEmptyView = (TextView)findViewById(R.id.empty);

        loadData();
        if(mMemoList.size() == 0)
        {
            mMemoListView.setEmptyView(mEmptyView);
        }
        else
        {
            mEmptyView.setVisibility(View.GONE);
            mMemoListView.setAdapter(mMemoListAdapter);
        }

        mMemoListView.setOnItemClickListener(this);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if (mDbAdapter != null)
        {
        	mDbAdapter.close();
        }
    }

    private Handler mHandler = new Handler()
    { 
        public void handleMessage(Message msg)
        {  
            if(msg.what == UPDATE_MESSAGE)
            {
                mMemoListAdapter.notifyDataSetChanged();
            } 
        } 
    };

    public void loadData()
    {
        mMemoList.removeAll(mMemoList);
        Cursor cursor = mDbAdapter.getAllMemo();
        String[] split = null;
        cursor.moveToFirst();

        if(cursor.getCount() == 1)
        {
            split = cursor.getString(3).split(" ");
            String title = cursor.getString(1);

            if(title.equals("") && !cursor.getString(3).equals(""))
            {
                title = cursor.getString(2).substring(0, 5);
            }
            else if(title.equals("") && cursor.getString(3).equals(""))
            {
                title = getResources().getString(R.string.no_title);
            }

            mMemoList.add(new MemoItem(cursor.getLong(0), title, split[0]));
        }

        if(cursor.getCount() > 1)
        {
            while(cursor.moveToNext())
            {
                split = cursor.getString(3).split(" ");
                String title = cursor.getString(1);

                if(title.equals("") && !cursor.getString(2).equals(""))
                {
                    if(cursor.getString(2).length() > 7)
                    {
                        title = cursor.getString(2).substring(0, 7) + "...";
                    }
                    else
                    {
                        title = cursor.getString(2);
                    }
                }
                else if(title.equals("") && cursor.getString(2).equals(""))
                {
                    title = getResources().getString(R.string.no_title);
                }

                Log.e(TAG, "memoid :" + cursor.getLong(0));
                mMemoList.add(new MemoItem(cursor.getLong(0), title, split[0]));
            }
        }

        cursor.close();
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
            case R.id.menu_check:
                if(mIsChecked) mIsChecked = false;
                else mIsChecked = true;
                mMemoListAdapter.setCheckBoxState(mIsChecked);
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
                loadData();
                mHandler.sendEmptyMessage(UPDATE_MESSAGE);
            break;
        }
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
    {
        Intent i = new Intent(this, ViewMemoActivity.class);
        i.putExtra("memoid", mMemoList.get(position).getMemoId());
        startActivity(i);
    }
}