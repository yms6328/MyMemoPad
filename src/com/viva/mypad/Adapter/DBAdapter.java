package com.viva.mypad.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viva.mypad.Constants.Constants.MemoConst;
import com.viva.mypad.DB.DBHelper;
import com.viva.mypad.Util.Util;

public class DBAdapter
{
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    private Context mCtx;

    public DBAdapter(Context ctx)
    {
        this.mCtx = ctx;
    }

    public DBAdapter open() throws SQLException
    {
        mDBHelper = new DBHelper(mCtx);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        mDB.close();
    }

    public long insertMemo(String title, String contents, String date)
    {
        Log.e("MYMEMOPAD------", title + ", " + contents + ", " + date);
        ContentValues values = new ContentValues();
        values.put(MemoConst.TITLE, title);
        values.put(MemoConst.CONTENTS, contents);
        values.put(MemoConst.DATE, date);

        return mDB.insert(MemoConst.TABLE_NAME, null, values);
    }

    public boolean updateMemo(long id, String title, String contents)
    {
        ContentValues values = new ContentValues();
        values.put(MemoConst.TITLE, title);
        values.put(MemoConst.CONTENTS, contents);
        values.put(MemoConst.DATE, Util.getNowDateTime());

        return mDB.update(MemoConst.TABLE_NAME, values, "_id=" + id, null) > 0;
    }

    public boolean deleteMemo(long id)
    {
        return mDB.delete(MemoConst.TABLE_NAME, "_id=" + id, null) > 0;
    }

    public Cursor getAllMemo()
    {
        // get memo data (sorted by date)
        return mDB.query(MemoConst.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getMemo(long id)
    {
        Cursor c = mDB.query(MemoConst.TABLE_NAME, null, "_id=" + id, null, null, null, null);
        if(c != null && c.getCount() != 0)
        {
            c.moveToFirst();
        }
        return c;
    }
}