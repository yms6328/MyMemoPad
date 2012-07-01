package com.viva.mypad;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.viva.mypad.Adapter.DBAdapter;
import com.viva.mypad.Util.Util;

public class WriteMemoActivity extends Activity
{
    private String mNow;
    private TextView mDateView;
    private EditText mEditTitle, mEditContent;
    private DBAdapter mDbAdapter;

    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.write_memo);

        mDbAdapter = new DBAdapter(this);
        mDbAdapter.open();

        mNow = Util.getNowDateTime();
        mDateView = (TextView)findViewById(R.id.dateView);
        mEditTitle = (EditText)findViewById(R.id.editTitle);
        mEditContent = (EditText)findViewById(R.id.editContent);

        mDateView.setText(mNow);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.write_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_save:
                mDbAdapter.insertMemo(mEditTitle.getText().toString(), mEditContent.getText().toString(), mNow, 0);
                this.setResult(RESULT_OK, null);
                this.finish();
            break;
        }
        return true;
    }
}
