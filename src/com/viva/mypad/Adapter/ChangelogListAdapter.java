package com.viva.mypad.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.viva.mypad.R;

public class ChangelogListAdapter extends BaseExpandableListAdapter
{
    private String[] mGroupData;
    private String[][] mChildData;
    private Context mContext;

    public ChangelogListAdapter(Context context)
    {
        mContext = context;
        mGroupData = context.getResources().getStringArray(R.array.changelog_group);
        mChildData = new String[mGroupData.length][];
        for(int i = 0; i < mGroupData.length; i++)
        {
            mChildData[i] = context.getResources().getStringArray(R.array.changelog_child003 + i);
        }
    }

    public String getChild(int groupPosition, int childPosition)
    {
        return mChildData[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
    	AbsListView.LayoutParams absLayoutParam = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView childTextView = new TextView(mContext);
        childTextView.setLayoutParams(absLayoutParam);
        childTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        childTextView.setPadding(80, 20, 20, 20);
        childTextView.setTextSize(15);
        childTextView.setBackgroundColor(Color.rgb(236, 236, 236));
        childTextView.setText(getChild(groupPosition, childPosition));
        return childTextView;
    }

    public int getChildrenCount(int groupPosition)
    {
        return mChildData[groupPosition].length;
    }

    public String getGroup(int groupPosition)
    {
        return mGroupData[groupPosition];
    }

    public int getGroupCount()
    {
        return mGroupData.length;
    }

    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        AbsListView.LayoutParams absLayoutParam = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView groupTextView = new TextView(mContext);
        groupTextView.setLayoutParams(absLayoutParam);
        groupTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        groupTextView.setPadding(70, 30, 30, 30);
        groupTextView.setTextSize(17);
        groupTextView.setText(getGroup(groupPosition));
        return groupTextView;
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
