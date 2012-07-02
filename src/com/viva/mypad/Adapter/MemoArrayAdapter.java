package com.viva.mypad.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viva.mypad.R;
import com.viva.mypad.WriteMemoActivity;
import com.viva.mypad.Item.MemoItem;

public class MemoArrayAdapter extends BaseAdapter
{
    private ArrayList<MemoItem> mMemoList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private boolean mIsImportant;
    private DBAdapter mDbAdapter;

    public MemoArrayAdapter(Context ctx, ArrayList<MemoItem> memoList, DBAdapter adapter)
    {
        mContext = ctx;
        mIsImportant = false;
        mMemoList = memoList;
        mLayoutInflater = LayoutInflater.from(ctx);
        mDbAdapter = adapter;
    }

    public int getCount()
    {
        return mMemoList.size();
    }

    public MemoItem getItem(int position)
    {
        return mMemoList.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = mLayoutInflater.inflate(R.layout.row_layout, null);
        TextView titleView = (TextView)convertView.findViewById(R.id.titleTextView);
        TextView dateView = (TextView)convertView.findViewById(R.id.dateTextView);
        Button importantButton = (Button)convertView.findViewById(R.id.importantButton);
        Button editButton = (Button)convertView.findViewById(R.id.editButton);
        Button deleteButton = (Button)convertView.findViewById(R.id.deleteButton);

        titleView.setText(mMemoList.get(position).getMemoTitle());
        dateView.setText(mMemoList.get(position).getMemoDate());

        if(mMemoList.get(position).getImportant() == 0)
        {
            importantButton.setBackgroundResource(R.drawable.ics_not_important);
        }
        else
        {
        	importantButton.setBackgroundResource(R.drawable.ics_rate_important);
        }

        editButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                Intent i = new Intent(mContext, WriteMemoActivity.class);
                i.putExtra("editMode", 1);
                i.putExtra("memoid", mMemoList.get(position).getMemoId());
                mContext.startActivity(i);
            }
        });

        deleteButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                mDbAdapter.deleteMemo(mMemoList.get(position).getMemoId());
                mMemoList.remove(position);
                notifyDataSetChanged();
            }
        });

        importantButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if(mIsImportant)
                {
                    v.setBackgroundResource(R.drawable.ics_not_important);
                    mIsImportant = false;
                    mDbAdapter.updateImportant(mMemoList.get(position).getMemoId(), 0);
                }
                else
                {
                    v.setBackgroundResource(R.drawable.ics_rate_important);
                    mIsImportant = true;
                    mDbAdapter.updateImportant(mMemoList.get(position).getMemoId(), 1);
                }
            }
        });

        return convertView;
    }

}
