package com.dugu.addressbook.adapter;

import android.view.View;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.recycleview.SimpleViewHolder;
import com.dugu.addressbook.adapter.recycleview.SortedListAdapter;
import com.dugu.addressbook.databinding.ItemContactInputBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

public class ContactInputMegSortedListAdapter extends SortedListAdapter<ContactInputItemViewModel, ItemContactInputBinding> {

    private OnItemElementClickListener<ContactInputItemViewModel> onClickListener;
    private OnItemElementClickListener<ContactInputItemViewModel> onTitleClickListener;
    private OnItemElementClickListener<ContactInputItemViewModel> onAddMoreInputBtnClickListener;

    public ContactInputMegSortedListAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(SimpleViewHolder<ItemContactInputBinding> holder, ItemContactInputBinding binding, final ContactInputItemViewModel obj, final int position) {
        super.handleViewHolder(holder, binding, obj, position);

        binding.itemContactInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(obj, position);
            }
        });

        binding.itemTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleClickListener.onClick(obj, position);
            }
        });

        binding.addMoreInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddMoreInputBtnClickListener.onClick(obj, position);
            }
        });


        if (obj.getSortKey() == Constants.SORTKEY_BIRTHDAY || obj.getSortKey() == Constants.SORTKEY_GROUP || obj.getSortKey() == Constants.SORTKEY_RING){
            binding.itemInput.setCursorVisible(false);
            binding.itemInput.setFocusable(false);
            binding.itemInput.setFocusableInTouchMode(false);
        }

        int leftIcon = getSortKeyIcon(obj.getSortKey());
        if (leftIcon != -1)
            binding.itemIcon.setImageResource(leftIcon);

    }

    private int getSortKeyIcon(int sortKey) {
        int src;
        switch (sortKey) {
            case Constants.SORTKEY_PHONE:
                src = R.drawable.vector_drawable_phone_icon;
                break;
            case Constants.SORTKEY_EMAIL:
                src = R.drawable.vector_drawable_email_icon;
                break;
            case Constants.SORTKEY_NICKNAME:
                src = R.drawable.vector_drawable_nickname_icon;
                break;
            case Constants.SORTKEY_ADDRESS:
                src = R.drawable.vector_drawable_address_icon;
                break;
            case Constants.SORTKEY_BIRTHDAY:
                src = R.drawable.vector_drawable_birthday_icon;
                break;
            case Constants.SORTKEY_GROUP:
                src = R.drawable.vector_drawable_group_icon;
                break;
            case Constants.SORTKEY_RING:
                src = R.drawable.vector_drawable_ring_icon;
                break;
            case Constants.SORTKEY_REMARK:
                src = R.drawable.vector_drawable_remark_icon;
                break;
            default:
                src = -1;
                break;
        }
        return src;
    }


    public OnItemElementClickListener<ContactInputItemViewModel> getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnItemElementClickListener<ContactInputItemViewModel> onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnItemElementClickListener<ContactInputItemViewModel> getOnTitleClickListener() {
        return onTitleClickListener;
    }

    public void setOnTitleClickListener(OnItemElementClickListener<ContactInputItemViewModel> onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public OnItemElementClickListener<ContactInputItemViewModel> getOnAddMoreInputBtnClickListener() {
        return onAddMoreInputBtnClickListener;
    }

    public void setOnAddMoreInputBtnClickListener(OnItemElementClickListener<ContactInputItemViewModel> onAddMoreInputBtnClickListener) {
        this.onAddMoreInputBtnClickListener = onAddMoreInputBtnClickListener;
    }
}
