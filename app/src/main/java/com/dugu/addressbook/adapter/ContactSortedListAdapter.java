package com.dugu.addressbook.adapter;

import android.view.View;

import com.dugu.addressbook.adapter.recycleview.SimpleViewHolder;
import com.dugu.addressbook.adapter.recycleview.SortedListAdapter;
import com.dugu.addressbook.databinding.ItemContactListBinding;
import com.dugu.addressbook.viewmodel.ContactItemViewModel;

public class ContactSortedListAdapter extends SortedListAdapter<ContactItemViewModel, ItemContactListBinding> {

    private String oldChar = "";

    public ContactSortedListAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(SimpleViewHolder<ItemContactListBinding> holder, ItemContactListBinding binding, ContactItemViewModel obj, int position) {
        super.handleViewHolder(holder, binding, obj, position);
        binding.contactIcon.setCircleBackgroundColor(obj.getRandomColor());
        if (oldChar.equals(""))
            oldChar = obj.getFirstPingYin();
        else if (obj.getFirstPingYin().equals(oldChar))
            binding.charIndex.setVisibility(View.INVISIBLE);
        else {
            oldChar = obj.getFirstPingYin();
        }

    }
}
