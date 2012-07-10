package com.viva.mypad.Comparator;

import java.util.Comparator;

import com.viva.mypad.Item.MemoItem;

public class ImportantComparator implements Comparator<MemoItem>
{
    public int compare(MemoItem one, MemoItem two)
    {
        return one.getImportant() > two.getImportant() ? -1 : (one.getImportant() == two.getImportant() ? 0 : 1);
    }
}