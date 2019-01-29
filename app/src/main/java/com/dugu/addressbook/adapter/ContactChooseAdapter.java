package com.dugu.addressbook.adapter;

import android.graphics.BitmapFactory;
import android.view.View;

import com.dugu.addressbook.adapter.recycleview.CommonViewAdapter;
import com.dugu.addressbook.databinding.ItemContactChooseBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.util.ColorGenerator;
import com.dugu.addressbook.viewmodel.item.ContactChooseItemViewModel;

public class ContactChooseAdapter extends CommonViewAdapter<ContactChooseItemViewModel, ItemContactChooseBinding> {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    private OnItemElementClickListener<ContactChooseItemViewModel> onClickListener;

    public ContactChooseAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(final ItemContactChooseBinding binding, final ContactChooseItemViewModel obj, final int position) {
        super.handleViewHolder(binding, obj, position);

        //设置联系人头像
        if (obj.getContact().getIcon() != null && obj.getContact().getIcon().length > 0) {
            binding.contactIcon.setImageBitmap(BitmapFactory.decodeByteArray(obj.getContact().getIcon(), 0, obj.getContact().getIcon().length));
        } else {
            binding.contactIcon.setCircleBackgroundColor(mColorGenerator.getColor(obj.getContact().getContact_id()));
        }

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

    public OnItemElementClickListener<ContactChooseItemViewModel> getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnItemElementClickListener<ContactChooseItemViewModel> onClickListener) {
        this.onClickListener = onClickListener;
    }
}
