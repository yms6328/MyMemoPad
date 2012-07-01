package com.viva.mypad.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viva.mypad.R;
import com.viva.mypad.Item.MemoItem;

public class MemoArrayAdapter extends BaseAdapter
{
    private ArrayList<MemoItem> mMemoList;
    private LayoutInflater mLayoutInflater;
    private boolean mCheckBoxVisible;
    private boolean mIsImportant;
    private DBAdapter mDbAdapter;

    public MemoArrayAdapter(Context ctx, ArrayList<MemoItem> memoList, DBAdapter adapter)
    {
        mCheckBoxVisible = false;
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

    public void setCheckBoxState(boolean isVisible){
        mCheckBoxVisible = isVisible;
        notifyDataSetChanged();
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = mLayoutInflater.inflate(R.layout.row_layout, null);
        TextView titleView = (TextView)convertView.findViewById(R.id.titleTextView);
        TextView dateView = (TextView)convertView.findViewById(R.id.dateTextView);
        CheckBox check = (CheckBox)convertView.findViewById(R.id.memoCheckBox);
        Button importantButton = (Button)convertView.findViewById(R.id.importantButton);
        Button editButton = (Button)convertView.findViewById(R.id.editButton);
        Button deleteButton = (Button)convertView.findViewById(R.id.deleteButton);

        titleView.setText(mMemoList.get(position).getMemoTitle());
        dateView.setText(mMemoList.get(position).getMemoDate());

        if(mCheckBoxVisible)
        {
            check.setVisibility(View.VISIBLE);
        }
        else
        {
            check.setVisibility(View.GONE);
        }

        editButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                ;
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
                }
                else
                {
                    v.setBackgroundResource(R.drawable.ics_rate_important);
                    mIsImportant = true;
                }
            }
        });

        return convertView;
    }

}
