package com.dugu.addressbook.adapter;

import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.recycleview.SimpleViewHolder;
import com.dugu.addressbook.adapter.recycleview.SortedListAdapter;
import com.dugu.addressbook.databinding.ItemContactInputBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

public class ContactInputMegSortedListAdapter extends SortedListAdapter<ContactInputItemViewModel, ItemContactInputBinding> {

    private OnItemElementClickListener<ContactInputItemViewModel> onTitleClickListener;
    private OnItemElementClickListener<ContactInputItemViewModel> onAddMoreInputBtnClickListener;

    public ContactInputMegSortedListAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder<ItemContactInputBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        EditText editText = holder.getBinding().itemInput;
        if (editText.getTag() != null) {
            editText.removeTextChangedListener((TextWatcher) editText.getTag());
        }
        EditContentWatcher contentWatcher = new EditContentWatcher(holder.getBinding().getContactInputItemViewModel());
        editText.addTextChangedListener(contentWatcher);
        editText.setTag(contentWatcher);
        editText.setText(holder.getBinding().getContactInputItemViewModel().getContent());
    }

    @Override
    protected void handleViewHolder(SimpleViewHolder<ItemContactInputBinding> holder, ItemContactInputBinding binding, final ContactInputItemViewModel obj, final int position) {
        super.handleViewHolder(holder, binding, obj, position);

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
            case Constants.SORTKEY_REMARK:
                src = R.drawable.vector_drawable_remark_icon;
                break;
            default:
                src = -1;
                break;
        }
        return src;
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
