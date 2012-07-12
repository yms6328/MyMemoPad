package com.viva.mypad;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.viva.mypad.Adapter.DBAdapter;
import com.viva.mypad.Constants.Constants.MemoConst;

public class MyMemoPadWidgetProvider extends AppWidgetProvider
{
    private DBAdapter mDbAdapter;

    public void onEnabled(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(context.getPackageName(), ".MyMemoPadWidgetProvider"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        final int N = appWidgetIds.length;

        for (int i = 0; i < N; i++)
        {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildViews(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private RemoteViews buildViews(Context context)
    {
        Log.i(MemoConst.TAG, "Update Widget!");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Editor edit = sharedPref.edit();

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        view.setOnClickPendingIntent(R.id.appButton, PendingIntent.getActivity(context, 0, new Intent(context, MyMemoPadActivity.class), 0));
        Intent intent = new Intent(context, WriteMemoActivity.class);
        intent.putExtra("editMode", 0);
        view.setOnClickPendingIntent(R.id.addMemoButton, PendingIntent.getActivity(context, 0, intent, 0));
        int current = sharedPref.getInt("current_memo", 0);

        mDbAdapter = new DBAdapter(context);
        mDbAdapter.open();

        Cursor cursor = mDbAdapter.getAllMemo();
        if(cursor.getCount() == 0)
        {
            view.setTextViewText(R.id.memoTitleView, context.getResources().getString(R.string.empty_memo_title));
            view.setTextViewText(R.id.memoContentView, context.getResources().getString(R.string.empty_memo_content));
        }
        else
        {
            cursor.moveToPosition(current);

            intent = new Intent(context, ViewMemoActivity.class);
            long id = cursor.getLong(0);
            intent.putExtra("memoid", id);
            view.setTextViewText(R.id.memoTitleView, cursor.getString(1));
            view.setTextViewText(R.id.memoContentView, cursor.getString(2));
            view.setTextViewText(R.id.memoDateView, cursor.getString(3));

            view.setOnClickPendingIntent(R.id.memoTitleView, PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
            view.setOnClickPendingIntent(R.id.memoContentView, PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));

            view.setOnClickPendingIntent(R.id.rightArrow, PendingIntent.getBroadcast(context, 0, new Intent(MemoConst.CLICK_NEXT_ACTION), PendingIntent.FLAG_UPDATE_CURRENT));
            view.setOnClickPendingIntent(R.id.leftArrow, PendingIntent.getBroadcast(context, 0, new Intent(MemoConst.CLICK_PREV_ACTION), PendingIntent.FLAG_UPDATE_CURRENT));
        }

        edit.putInt("current_memo", current);
        edit.putInt("all_memo", cursor.getCount());
        edit.commit();

        cursor.close();
        mDbAdapter.close();
        return view;
    }

    public void onReceive(Context context, Intent getIntent)
    {
        DBAdapter dbAdapter = new DBAdapter(context);
        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllMemo();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPref.edit();

        int currentMemo = sharedPref.getInt("current_memo", 0);
        int allMemo = cursor.getCount();

        if(MemoConst.CLICK_NEXT_ACTION.equals(getIntent.getAction()))
        {
            if(allMemo == 0)
            {
                // do nothing
            }
            else
            {
                currentMemo += 1;
                if(currentMemo == allMemo)
                {
                    currentMemo = allMemo - 1;
                    Toast.makeText(context, context.getResources().getString(R.string.last_memo), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    cursor.moveToPosition(currentMemo);
                    update(context, updateView(context, cursor));
                }

                editor.putInt("current_memo", currentMemo);
                editor.commit();
            }
        }
        else if(MemoConst.CLICK_PREV_ACTION.equals(getIntent.getAction()))
        {
            if(allMemo == 0)
            {
                // do nothing
            }
            else
            {
                currentMemo -= 1;
                if(currentMemo < 0)
                {
                    currentMemo = 0;
                    Toast.makeText(context, context.getResources().getString(R.string.first_memo), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    cursor.moveToPosition(currentMemo);
                    update(context, updateView(context, cursor));
                }

                editor.putInt("current_memo", currentMemo);
                editor.commit();
            }
        }
        else if(MemoConst.ADDED_MEMO.equals(getIntent.getAction()))
        {
            if(allMemo == 1)
            {
                cursor.moveToPosition(0);
                currentMemo = 0;
                update(context, updateView(context, cursor));
            }

            editor.putInt("all_memo", allMemo);
            editor.putInt("current_memo", currentMemo);
            editor.commit();
        }
        else if(MemoConst.EDITED_MEMO.equals(getIntent.getAction()))
        {
            long updatedMemoId = getIntent.getExtras().getLong("updated_memoid");
            cursor.moveToPosition(currentMemo);

            if(updatedMemoId == cursor.getLong(0))
            {
                update(context, updateView(context, cursor));
            }
        }
        else if(MemoConst.DELETED_MEMO.equals(getIntent.getAction()))
        {
            if(allMemo != 0 && currentMemo == allMemo)
            {
                currentMemo -= 1;
                cursor.moveToPosition(currentMemo);
                update(context, updateView(context, cursor));
            }
            else if(allMemo == 0)
            {
            	update(context, updateEmptyView(context));
            }
            editor.putInt("all_memo", allMemo);
            editor.putInt("current_memo", currentMemo);
            editor.commit();
        }
        else if(MemoConst.DELETED_ALL_MEMO.equals(getIntent.getAction()))
        {
        	update(context, updateEmptyView(context));
            currentMemo = 0;
            editor.putInt("all_memo", allMemo);
            editor.putInt("current_memo", currentMemo);
            editor.commit();
        }
        else
        {
            super.onReceive(context, getIntent);
        }
        Log.i(MemoConst.TAG, "current: " + currentMemo + " all: " + allMemo);
        cursor.close();
        dbAdapter.close();
    }

    private RemoteViews updateView(Context context, Cursor c)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.memoTitleView, c.getString(1));
        if(c.getString(2).equals(""))
        {
            views.setTextViewText(R.id.memoContentView, context.getResources().getString(R.string.no_memo));
        }
        else
        {
            views.setTextViewText(R.id.memoContentView, c.getString(2));
        }

        views.setTextViewText(R.id.memoDateView, c.getString(3));

        long id = c.getLong(0);
        Intent intent = new Intent(context, ViewMemoActivity.class);
        intent.putExtra("memoid", id);
        views.setOnClickPendingIntent(R.id.memoTitleView, PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        views.setOnClickPendingIntent(R.id.memoContentView, PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        
        return views;
    }

    private RemoteViews updateEmptyView(Context context)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.memoTitleView, context.getResources().getString(R.string.empty_memo_title));
        views.setTextViewText(R.id.memoContentView, context.getResources().getString(R.string.empty_memo_content));
        views.setTextViewText(R.id.memoDateView, "");

        return views;
    }

    private void update(Context context, RemoteViews views)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName cpName = new ComponentName(context, MyMemoPadWidgetProvider.class);
        appWidgetManager.updateAppWidget(cpName, views);
    }
}
