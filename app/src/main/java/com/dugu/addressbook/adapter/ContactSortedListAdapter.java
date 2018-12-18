package com.dugu.addressbook.adapter;

import android.graphics.Color;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.recycleview.SimpleViewHolder;
import com.dugu.addressbook.adapter.recycleview.SortedListAdapter;
import com.dugu.addressbook.databinding.ItemContactListBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.ContactItemViewModel;

import java.util.HashMap;

public class ContactSortedListAdapter extends SortedListAdapter<ContactItemViewModel, ItemContactListBinding> {

    private OnItemElementClickListener<ContactItemViewModel> onClickListener;
    private OnItemElementClickListener<ContactItemViewModel> onLongClickListener;

    private String oldChar = "";

    private HashMap<String, Integer> indexMap = new HashMap<>();

    public ContactSortedListAdapter() {
        super();
    }

    @Override
    protected void handleViewHolder(SimpleViewHolder<ItemContactListBinding> holder, ItemContactListBinding binding, final ContactItemViewModel obj, final int position) {
        super.handleViewHolder(holder, binding, obj, position);

        //设置联系人头像
        if (obj.getIcon() != null && obj.getIcon().length > 0) {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(AddressBookApplication.getAppContext()).load(obj.getIcon())
                    .apply(options).into(binding.contactIcon);
        } else {
            binding.contactIcon.setCircleBackgroundColor(obj.getRandomColor());
        }

        //设置sideBar必要数据
        if (oldChar.equals("")) {
            oldChar = obj.getFirstPingYin();
            indexMap.put(oldChar, position);
        } else if (obj.getFirstPingYin().equals(oldChar))
            binding.charIndex.setVisibility(View.INVISIBLE);
        else {
            oldChar = obj.getFirstPingYin();
            indexMap.put(oldChar, position);
        }

        if (obj.getContact_id() < 0) {
            binding.simIcon.setVisibility(View.GONE);
            binding.rightIcon.setVisibility(View.VISIBLE);

            // 非正式联系人的灰底
            if (obj.getName().equals("群组")){
                binding.contactIcon.setImageResource(R.drawable.vector_drawable_group_main_icon);
                binding.contactIcon.setCircleBackgroundColor(Color.parseColor("#bfbfbf"));
            }else if (obj.getName().equals("名片夹")){
                binding.contactIcon.setImageResource(R.drawable.vector_drawable_card_contact);
                binding.contactIcon.setCircleBackgroundColor(Color.parseColor("#bfbfbf"));
            }
        }

        binding.contacrItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(obj, position);
            }
        });
        binding.contacrItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickListener.onClick(obj, position);
                return true;
            }
        });
    }

    public HashMap<String, Integer> getIndexMap() {
        return indexMap;
    }

    public OnItemElementClickListener<ContactItemViewModel> getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnItemElementClickListener<ContactItemViewModel> onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnItemElementClickListener<ContactItemViewModel> getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(OnItemElementClickListener<ContactItemViewModel> onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }
}
