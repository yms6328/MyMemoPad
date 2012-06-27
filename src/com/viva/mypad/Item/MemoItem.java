package com.viva.mypad.Item;

public class MemoItem
{
    private String mMemoTitle, mMemoDate;

    public MemoItem(String title, String date)
    {
        mMemoTitle = title;
        mMemoDate = date;
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
