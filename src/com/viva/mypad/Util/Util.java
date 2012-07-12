package com.viva.mypad.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

public class Util
{
    private static final int BUFFER_SIZE = 512;
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
    private static String mFilePath;

    public static String getNowDateTime()
    {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdfNow.format(date);
    }

    public final static String writeLog(Context context)
    {
        String[] dateTime = getNowDateTime().split(" ");
        String[] date = dateTime[0].split("/");
        String parsedDate = date[0] + "_" + date[1] + "_" + date[2];
        String parsedFileName = parsedDate + "_" + dateTime[1];

        File f = new File(SD_PATH, "mymemopad_" + parsedFileName + ".txt");
        String filePath = f.getPath();
        mFilePath = filePath;

        String[] LOGCAT_CMD = new String[]
        {
            "logcat",
            "-d",
            "AndroidRuntime:E System.err:* AndroidRuntime:V AndroidRuntime:I"
        };

        Process logcatProc = null;
        try
        {
            logcatProc = Runtime.getRuntime().exec(LOGCAT_CMD);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        BufferedReader reader = null;
        BufferedWriter writer = null;
        String lineSeparatoer = System.getProperty("line.separator");
        StringBuilder strOutput = new StringBuilder();
        try
        {
            reader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), BUFFER_SIZE);
            writer = new BufferedWriter(new FileWriter(f));
            String line;

            while ((line = reader.readLine()) != null)
            {
                strOutput.append(line);
                strOutput.append(lineSeparatoer);
                writer.write(line);
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return filePath;
    }

    public static boolean deleteLogFile()
    {
        return new File(mFilePath).delete();
    }

    public static String getAppVersion(Context context)
    {
        String version = "1.0";

        try
        {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = i.versionName;
        }
        catch(NameNotFoundException e)
        {
            ;
        }
        return version;
    }
}
