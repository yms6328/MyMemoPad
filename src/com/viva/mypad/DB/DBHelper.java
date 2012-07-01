package com.viva.mypad.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.viva.mypad.Constants.Constants.MemoConst;

public class DBHelper extends SQLiteOpenHelper
{
    private final String TAG = "SIMPLE_MEMO";

    public DBHelper(Context context)
    {
        super(context, MemoConst.DB_NAME, null, MemoConst.DB_VER);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + MemoConst.TABLE_NAME + "(" 
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + MemoConst.COL_TITLE + " TEXT, "
                    + MemoConst.COL_CONTENTS + " TEXT, "
                    + MemoConst.COL_DATE + " TEXT, "
                    + MemoConst.COL_IMPORTANT + " INTEGER);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(TAG, "Version Upgrade");
        db.execSQL("DROP TABLE " + MemoConst.TABLE_NAME);
        onCreate(db);
    }
}
