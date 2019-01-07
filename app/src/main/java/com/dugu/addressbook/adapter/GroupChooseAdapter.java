package com.dugu.addressbook.adapter;

import android.view.View;

import com.dugu.addressbook.adapter.recycleview.CommonViewAdapter;
import com.dugu.addressbook.databinding.ItemGroupChooseBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.GroupChooseItemViewModel;

public class GroupChooseAdapter extends CommonViewAdapter<GroupChooseItemViewModel,ItemGroupChooseBinding> {

    private OnItemElementClickListener<GroupChooseItemViewModel> onClickListener;

    public GroupChooseAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(final ItemGroupChooseBinding binding, final GroupChooseItemViewModel obj, final int position) {
        super.handleViewHolder(binding, obj, position);
        binding.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj.isChecked()) {
                    obj.setChecked(false);
                    binding.itemCheckbox.setChecked(false);
                }
                else {
                    obj.setChecked(true);
                    binding.itemCheckbox.setChecked(true);
                }
                onClickListener.onClick(obj, position);
            }
        });
    }

    public OnItemElementClickListener<GroupChooseItemViewModel> getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnItemElementClickListener<GroupChooseItemViewModel> onClickListener) {
        this.onClickListener = onClickListener;
    }
}
