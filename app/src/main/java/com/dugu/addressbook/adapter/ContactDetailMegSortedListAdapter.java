package com.dugu.addressbook.adapter;

import android.view.View;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.recycleview.SimpleViewHolder;
import com.dugu.addressbook.adapter.recycleview.SortedListAdapter;
import com.dugu.addressbook.databinding.ItemContactDetailBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.ContactDetailItemViewModel;

public class ContactDetailMegSortedListAdapter extends SortedListAdapter<ContactDetailItemViewModel, ItemContactDetailBinding> {

    private int oldSortKey = -1;

    private OnItemElementClickListener<ContactDetailItemViewModel> onClickListener;
    private OnItemElementClickListener<ContactDetailItemViewModel> onLongClickListener;
    private OnItemElementClickListener<ContactDetailItemViewModel> onRightBtnClickListener;

    public ContactDetailMegSortedListAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(SimpleViewHolder<ItemContactDetailBinding> holder, ItemContactDetailBinding binding, final ContactDetailItemViewModel obj, final int position) {
        super.handleViewHolder(holder, binding, obj, position);

        binding.contactMegDetailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(obj, position);
            }
        });
        binding.contactMegDetailItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickListener.onClick(obj, position);
                return true;
            }
        });
        binding.rightLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightBtnClickListener.onClick(obj,position);
            }
        });

        if (oldSortKey == -1)
            oldSortKey = obj.getSortKey();
        else if (obj.getSortKey() == oldSortKey)
            binding.itemDetailIcon.setVisibility(View.INVISIBLE);
        else if (obj.getSortKey() != oldSortKey)
            oldSortKey = obj.getSortKey();

        int leftIcon = getSortKeyIcon(obj.getSortKey());
        if (leftIcon != -1)
            binding.itemDetailIcon.setImageResource(leftIcon);

        int rightIcon = getRightIcon(obj.getSortKey());
        if (rightIcon == -1){
            binding.rightIcon.setVisibility(View.INVISIBLE);
        }else {
            binding.rightIcon.setImageResource(rightIcon);
        }
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

    private int getRightIcon(int sortKey) {
        int src;
        switch (sortKey) {
            case Constants.SORTKEY_PHONE:
                src = R.drawable.vector_drawable_sms_message;
                break;
            case Constants.SORTKEY_EMAIL:
            case Constants.SORTKEY_RING:
            case Constants.SORTKEY_ADDRESS:
                src = R.drawable.vector_drawable_right_arrow;
                break;
            default:
                src = -1;
                break;
        }
        return src;
    }

    public OnItemElementClickListener<ContactDetailItemViewModel> getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnItemElementClickListener<ContactDetailItemViewModel> onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnItemElementClickListener<ContactDetailItemViewModel> getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(OnItemElementClickListener<ContactDetailItemViewModel> onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public OnItemElementClickListener<ContactDetailItemViewModel> getOnRightBtnClickListener() {
        return onRightBtnClickListener;
    }

    public void setOnRightBtnClickListener(OnItemElementClickListener<ContactDetailItemViewModel> onRightBtnClickListener) {
        this.onRightBtnClickListener = onRightBtnClickListener;
    }
}
