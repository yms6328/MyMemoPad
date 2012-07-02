package com.viva.mypad.Item;

public class MemoItem
{
    private long mMemoId;
    private String mMemoTitle, mMemoDate;
    private int mIsImportant;

    public MemoItem(long id, String title, String date, int important)
    {
        mMemoId = id;
        mMemoTitle = title;
        mMemoDate = date;
        mIsImportant = important;
    }

    public long getMemoId()
    {
        return mMemoId;
    }

    public String getMemoTitle()
    {
        return mMemoTitle;
    }

    public String getMemoDate()
    {
        return mMemoDate;
    }

    public int getImportant()
    {
        return mIsImportant;
    }
}
