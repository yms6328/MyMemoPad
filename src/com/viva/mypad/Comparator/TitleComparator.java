package com.viva.mypad.Comparator;

import java.util.Comparator;

import com.viva.mypad.Item.MemoItem;


public class TitleComparator implements Comparator<MemoItem>
{
    public int compare(MemoItem one, MemoItem two)
    {
        return one.getMemoTitle().compareTo(two.getMemoTitle());
    }
}
