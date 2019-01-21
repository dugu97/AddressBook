package com.dugu.addressbook.adapter;

import android.graphics.BitmapFactory;
import android.view.View;

import com.dugu.addressbook.adapter.recycleview.CommonViewAdapter;
import com.dugu.addressbook.databinding.ItemSearchContactBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.util.ColorGenerator;
import com.dugu.addressbook.viewmodel.item.SearchContactItemViewModel;

public class SearchContactAdapter extends CommonViewAdapter<SearchContactItemViewModel, ItemSearchContactBinding> {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    private OnItemElementClickListener<SearchContactItemViewModel> onClickListener;

    public SearchContactAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(final ItemSearchContactBinding binding, final SearchContactItemViewModel obj, final int position) {
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
                onClickListener.onClick(obj, position);
            }
        });
    }

    public OnItemElementClickListener<SearchContactItemViewModel> getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnItemElementClickListener<SearchContactItemViewModel> onClickListener) {
        this.onClickListener = onClickListener;
    }
}
