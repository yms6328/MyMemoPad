package com.viva.mypad.InfoActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.viva.mypad.R;

public class ChangeLogActivity extends Activity
{
    private ExpandableListView mChangelogListView;
    private ExpandableListAdapter mListAdapter;

    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.changelog_layout);

        mChangelogListView = (ExpandableListView)findViewById(R.id.changelog_list);
        mListAdapter = mChangelogListView.getExpandableListAdapter();
    }
}
