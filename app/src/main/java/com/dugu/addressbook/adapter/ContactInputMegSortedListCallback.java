package com.dugu.addressbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

public class ContactInputMegSortedListCallback extends SortedListAdapterCallback<ContactInputItemViewModel> {

    public ContactInputMegSortedListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    /**
     * 排序条件
     */
    @Override
    public int compare(ContactInputItemViewModel o1, ContactInputItemViewModel o2) {
        if (o1.getSortKey() < o2.getSortKey())
            return -1;
        else if (o1.getSortKey() == o2.getSortKey()) {
            if (o1.getSerialNumber() < o2.getSerialNumber())
                return -1;
            else if (o1.getSerialNumber() > o2.getSerialNumber())
                return 1;
            else
                return 0;
        } else
            return 1;
    }

    /**
     * 用来判断两个对象是否是相同的Item。
     */
    @Override
    public boolean areItemsTheSame(ContactInputItemViewModel item1, ContactInputItemViewModel item2) {
        return item1.getSortKey() == item2.getSortKey() && item1.getSerialNumber() == item2.getSerialNumber();
    }


    //TODO 判断两个对象是否是内容相同的Item

    /**
     * 用来判断两个对象是否是内容相同的Item。
     */
    @Override
    public boolean areContentsTheSame(ContactInputItemViewModel oldItem, ContactInputItemViewModel newItem) {
        if (oldItem.getSortKey() == newItem.getSortKey() && oldItem.getSerialNumber() == newItem.getSerialNumber()) {
            return false;
        }
        return true;
    }
}