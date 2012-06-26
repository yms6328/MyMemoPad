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

        public static final String DB_NAME = "simplememopad.db";
        public static final int DB_VER = 1;

        public static final String TABLE_NAME = "memo";
        public static final String TITLE = "title";
        public static final String CONTENTS = "contents";
        public static final String DATE = "date";
        public static final String LAST_MOD = "last_modify";
    }
}
