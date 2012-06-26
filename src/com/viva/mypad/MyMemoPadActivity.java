package com.viva.mypad;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.viva.mypad.DB.DBHelper;

public class MyMemoPadActivity extends Activity
{
	private DBHelper dbHelper;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init();
        ActionBar actionBar = getActionBar();
        actionBar.show();
        actionBar.setTitle("Memo List");
    }

    public void loadData()
    {
        ;
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        return true;
    }

    public void init()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sharedPref.contains("initialPref"))
        {
            dbHelper = new DBHelper(this);
            Editor edit = sharedPref.edit();
            edit.putBoolean("initialPref", true);
            edit.commit();
            Toast.makeText(this, this.getResources().getString(R.string.create_db), Toast.LENGTH_SHORT).show();
        }
    }
}