package com.dugu.addressbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.dugu.addressbook.viewmodel.item.ContactDetailItemViewModel;

public class ContactDetailMegSortedListCallback extends SortedListAdapterCallback<ContactDetailItemViewModel> {

    public ContactDetailMegSortedListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    /**
     * 排序条件
     */
    @Override
    public int compare(ContactDetailItemViewModel o1, ContactDetailItemViewModel o2) {
        if (o1.getSortKey() < o2.getSortKey())
            return -1;
        else if (o1.getSortKey() == o2.getSortKey())
            return 0;
        else
            return 1;
    }

    /**
     * 用来判断两个对象是否是相同的Item。
     */
    @Override
    public boolean areItemsTheSame(ContactDetailItemViewModel item1, ContactDetailItemViewModel item2) {
        return item1.getSortKey() == item2.getSortKey() && item1.getContent().equals(item2.getContent()) && item1.getTitle().equals(item2.getTitle());
    }


    //TODO 判断两个对象是否是内容相同的Item
    /**
     * 用来判断两个对象是否是内容相同的Item。
     */
    @Override
    public boolean areContentsTheSame(ContactDetailItemViewModel oldItem, ContactDetailItemViewModel newItem) {
        if (oldItem.getSortKey() != newItem.getSortKey()) {
            return false;
        }
        return true;
    }
}