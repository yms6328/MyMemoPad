package com.viva.mypad.Constants;

import android.provider.BaseColumns;

public final class Constants implements BaseColumns
{
    private Constants()
    {
        ;
    }

    public static final class MemoConst implements BaseColumns
    {
        private MemoConst()
        {
            ;
        }

        public static final String DB_NAME = "mymemopad.db";
        public static final int DB_VER = 2;

        public static final String TABLE_NAME = "mymemo";
        public static final String COL_TITLE = "title";
        public static final String COL_CONTENTS = "contents";
        public static final String COL_DATE = "date";
        public static final String COL_IMPORTANT = "important";

        public static final String CLICK_NEXT_ACTION = "com.viva.mypad.CLICK_NEXT";
        public static final String CLICK_PREV_ACTION = "com.viva.mypad.CLICK_PREV";
        public static final String ADDED_MEMO = "com.viva.mypad.ADDED_MEMO";
        public static final String EDITED_MEMO = "com.viva.mypad.EDITED_MEMO";
        public static final String DELETED_MEMO = "com.viva.mypad.DELETED_MEMO";
        public static final String DELETED_ALL_MEMO = "com.viva.mypad.DELETED_ALL_MEMO";

        public static final String TAG = "[MyMemopad]";
    }
}
