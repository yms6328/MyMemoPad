package com.viva.mypad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.viva.mypad.Adapter.ChangelogListAdapter;
import com.viva.mypad.Util.Util;

public class ChangeLogActivity extends Activity
{
    private ExpandableListView mChangelogListView;
    private ChangelogListAdapter mChangelogListAdapter;
    private TextView mVersionTextView;

    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.changelog_layout);

        mChangelogListView = (ExpandableListView)findViewById(R.id.changelog_list);
        mChangelogListAdapter = new ChangelogListAdapter(this);
        mChangelogListView.setAdapter(mChangelogListAdapter);
        mVersionTextView = (TextView)findViewById(R.id.versionStatusTextView);
        mVersionTextView.setText(getResources().getString(R.string.info_version_status) + " v" + Util.getAppVersion(this));
    }
}
