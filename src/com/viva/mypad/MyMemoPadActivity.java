package com.viva.mypad;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private final int INSERT_OK = 0;
    private final int UPDATE_MESSAGE = 100;

    private DBAdapter mDbAdapter;
    private ListView mMemoListView;
    private ArrayList<MemoItem> mMemoList;
    private MemoArrayAdapter mMemoListAdapter;
    private TextView mEmptyView;

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

    public void onResume()
    {
        super.onResume();
        loadData();
        mHandler.sendEmptyMessage(UPDATE_MESSAGE);
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
        cursor.moveToFirst();

        if(cursor.getCount() == 1)
        {
            mMemoList.add(new MemoItem(cursor.getLong(0), 
                                       getTitle(cursor.getString(1), cursor.getString(2)), 
                                       getDateFromDateTime(cursor.getString(3)),
                                       cursor.getInt(4)));
        }

        if(cursor.getCount() > 1)
        {
            while(cursor.moveToNext())
            {
                mMemoList.add(new MemoItem(cursor.getLong(0), 
                                           getTitle(cursor.getString(1), 
                                           cursor.getString(2)),
                                           getDateFromDateTime(cursor.getString(3)),
                                           cursor.getInt(4)));
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
        Intent intent;
        switch(item.getItemId())
        {
            case R.id.menu_add:
                intent = new Intent(this, WriteMemoActivity.class);
                intent.putExtra("editMode", 0);
                startActivityForResult(intent, INSERT_OK);
            break;

            case R.id.menu_delete_all_memo:
                showDeleteAllDialog();
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

    public void showDeleteAllDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.alert_delete_title));
        alert.setMessage(getResources().getString(R.string.alert_delete_all_message));

        alert.setPositiveButton(getResources().getString(R.string.alert_delete_confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                mDbAdapter.deleteAllMemo();
                dialog.dismiss();
                loadData();
                mHandler.sendEmptyMessage(UPDATE_MESSAGE);
            }
        });

        alert.setNegativeButton(getResources().getString(R.string.alert_delete_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private String getDateFromDateTime(String date)
    {
        return date.split(" ")[0];
    }

    private String getTitle(String dbTitle, String dbContent)
    {
        String title = dbTitle;
        if(title.equals("") && !dbContent.equals(""))
        {
            if(dbContent.length() > 7)
            {
                title = dbContent.substring(0, 7) + "...";
            }
            else
            {
                title = dbContent;
            }
        }

        return title;
    }
}