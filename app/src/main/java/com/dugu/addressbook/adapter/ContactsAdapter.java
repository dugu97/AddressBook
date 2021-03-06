package com.dugu.addressbook.adapter;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.recycleview.CommonViewAdapter;
import com.dugu.addressbook.adapter.recycleview.SimpleViewHolder;
import com.dugu.addressbook.databinding.ItemContactListBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.util.ColorGenerator;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends CommonViewAdapter<ContactItemViewModel, ItemContactListBinding> {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    private OnItemElementClickListener<ContactItemViewModel> onClickListener;
    private OnItemElementClickListener<ContactItemViewModel> onLongClickListener;

    public ContactsAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder<ItemContactListBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        ContactItemViewModel obj = mItems.get(position);

        CircleImageView imageView =  holder.getBinding().contactIcon;
        if (imageView.getTag() != null){
            imageView.setImageResource((Integer) imageView.getTag());
        }
        imageView.setTag(R.drawable.vector_drawable_contact_default_icon);

        //设置联系人头像
        if (obj.getContact().getIcon() != null && obj.getContact().getIcon().length > 0) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(obj.getContact().getIcon(), 0, obj.getContact().getIcon().length));
        } else {
            holder.getBinding().contactIcon.setCircleBackgroundColor(mColorGenerator.getColor(obj.getContact().getContact_id()));
        }

        //设置工具栏item 的图标
        if (obj.getContact().getContact_id() < Constants.LIST_UTIL_INDEX) {
            // 非正式联系人的灰底
            if (obj.getContact().getName().equals("群组")){
                holder.getBinding().contactIcon.setImageResource(R.drawable.vector_drawable_group_main_icon);
                holder.getBinding().contactIcon.setCircleBackgroundColor(Color.parseColor("#CDCDCD"));
            }else if (obj.getContact().getName().equals("名片夹")){
                holder.getBinding().contactIcon.setImageResource(R.drawable.vector_drawable_card_contact);
                holder.getBinding().contactIcon.setCircleBackgroundColor(Color.parseColor("#CDCDCD"));
            }
        }
    }

    @Override
    protected void handleViewHolder(SimpleViewHolder<ItemContactListBinding> holder, ItemContactListBinding binding, final ContactItemViewModel obj, final int position) {
        super.handleViewHolder(holder, binding, obj, position);

        binding.contactItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(obj, position);
            }
        });
        binding.contactItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickListener.onClick(obj, position);
                return true;
            }
        });

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
