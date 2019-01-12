package com.dugu.addressbook.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.adapter.recycleview.CommonViewAdapter;
import com.dugu.addressbook.adapter.recycleview.SimpleViewHolder;
import com.dugu.addressbook.databinding.ItemGroupDetailBinding;
import com.dugu.addressbook.util.ColorGenerator;
import com.dugu.addressbook.viewmodel.item.GroupDetailItemViewModel;

public class GroupDetailAdapter extends CommonViewAdapter<GroupDetailItemViewModel, ItemGroupDetailBinding> {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    public GroupDetailAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(SimpleViewHolder<ItemGroupDetailBinding> holder, ItemGroupDetailBinding binding, GroupDetailItemViewModel obj, int position) {
        super.handleViewHolder(holder, binding, obj, position);

        //设置联系人头像
        if (obj.getContact().getIcon() != null && obj.getContact().getIcon().length > 0) {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(AddressBookApplication.getAppContext()).load(obj.getContact().getIcon())
                    .apply(options).into(binding.contactIcon);
        } else {
            binding.contactIcon.setCircleBackgroundColor(mColorGenerator.getColor(obj.getContact().getContact_id()));
        }
    }

}
