package com.viva.mypad.Item;

public class MemoItem
{
    private long mMemoId;
    private String mMemoTitle, mMemoDate;

    public MemoItem(long id, String title, String date)
    {
        mMemoId = id;
        mMemoTitle = title;
        mMemoDate = date;
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
}
