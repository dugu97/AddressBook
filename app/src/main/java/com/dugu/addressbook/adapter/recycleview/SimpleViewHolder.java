package com.dugu.addressbook.adapter.recycleview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 简单通用RecyclerView数据绑定的adapter的ViewHolder
 */
public class SimpleViewHolder<TD extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private TD binding;

    public SimpleViewHolder(View itemView) {
        super(itemView);
    }

    public TD getBinding() {
        return binding;
    }

    public void setBinding(TD binding) {
        this.binding = binding;
    }
}
