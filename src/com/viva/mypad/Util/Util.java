package com.viva.mypad.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util
{
    public static String getNowDateTime()
    {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdfNow.format(date);
    }
}
