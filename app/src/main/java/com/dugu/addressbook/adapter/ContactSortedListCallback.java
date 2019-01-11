package com.dugu.addressbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

public class ContactSortedListCallback extends SortedListAdapterCallback<ContactItemViewModel> {

    public ContactSortedListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    /**
     * 排序条件
     */
    @Override
    public int compare(ContactItemViewModel o1, ContactItemViewModel o2) {
        if (o1.getFirstPingYin().equals(o2.getFirstPingYin()))
            return o1.getContact().getContact_id().compareTo(o2.getContact().getContact_id());
        return o1.getFirstPingYin().compareTo(o2.getFirstPingYin());
    }

    /**
     * 用来判断两个对象是否是相同的Item。
     */
    @Override
    public boolean areItemsTheSame(ContactItemViewModel item1, ContactItemViewModel item2) {
        return item1.getContact().getContact_id() == item2.getContact().getContact_id();
    }


    //TODO 判断两个对象是否是内容相同的Item
    /**
     * 用来判断两个对象是否是内容相同的Item。
     */
    @Override
    public boolean areContentsTheSame(ContactItemViewModel oldItem, ContactItemViewModel newItem) {
        if (oldItem.getContact().getContact_id() != newItem.getContact().getContact_id()) {
            return false;
        }
        return oldItem.getContact().getContact_id().equals(newItem.getContact().getContact_id());
    }
}