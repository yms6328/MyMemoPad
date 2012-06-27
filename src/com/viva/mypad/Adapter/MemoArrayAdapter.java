package com.viva.mypad.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viva.mypad.R;
import com.viva.mypad.Item.MemoItem;

public class MemoArrayAdapter extends BaseAdapter
{
    private ArrayList<MemoItem> mMemoList;
    private LayoutInflater mLayoutInflater;

    public MemoArrayAdapter(Context ctx, ArrayList<MemoItem> memoList)
    {
        mMemoList = memoList;
        mLayoutInflater = LayoutInflater.from(ctx);
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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = mLayoutInflater.inflate(R.layout.row_layout, null);
        TextView titleView = (TextView)convertView.findViewById(R.id.titleTextView);
        TextView dateView = (TextView)convertView.findViewById(R.id.dateTextView);

        titleView.setText(mMemoList.get(position).getMemoTitle());
        dateView.setText(mMemoList.get(position).getMemoDate());

        return convertView;
    }

}
