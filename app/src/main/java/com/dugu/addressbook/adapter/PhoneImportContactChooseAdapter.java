package com.dugu.addressbook.adapter;

import android.view.View;

import com.dugu.addressbook.adapter.recycleview.CommonViewAdapter;
import com.dugu.addressbook.databinding.ItemPhoneImportContactChooseBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.util.ColorGenerator;
import com.dugu.addressbook.viewmodel.item.PhoneImportContactChooseItemViewModel;

public class PhoneImportContactChooseAdapter extends CommonViewAdapter<PhoneImportContactChooseItemViewModel, ItemPhoneImportContactChooseBinding> {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    private OnItemElementClickListener<PhoneImportContactChooseItemViewModel> onClickListener;

    public PhoneImportContactChooseAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(final ItemPhoneImportContactChooseBinding binding, final PhoneImportContactChooseItemViewModel obj, final int position) {
        super.handleViewHolder(binding, obj, position);


        binding.contactIcon.setCircleBackgroundColor(mColorGenerator.getColor(position));

        binding.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj.isChecked()) {
                    obj.setChecked(false);
                    binding.itemCheckbox.setChecked(false);
                } else {
                    obj.setChecked(true);
                    binding.itemCheckbox.setChecked(true);
                }
                onClickListener.onClick(obj, position);
            }
        });
    }

    public OnItemElementClickListener<PhoneImportContactChooseItemViewModel> getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnItemElementClickListener<PhoneImportContactChooseItemViewModel> onClickListener) {
        this.onClickListener = onClickListener;
    }
}
