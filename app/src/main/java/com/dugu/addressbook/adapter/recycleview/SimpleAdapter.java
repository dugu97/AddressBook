package com.dugu.addressbook.adapter.recycleview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

public class SimpleAdapter<T> extends RecyclerView.Adapter<SimpleViewHolder> {
    /**
     * 数据集
     */
    protected List<T> mDatas;

    /**
     * item布局id
     */
    protected int itemLayoutId;

    /**
     * 对象BRid
     */
    protected int itemBrId;

    protected AdapterView.OnItemClickListener onItemClickListener;

    protected AdapterView.OnItemLongClickListener onItemLongClickListener;

    public SimpleAdapter(List<T> mDatas, int itemLayoutId, int itemBrId) {
        this.mDatas = mDatas;
        this.itemLayoutId = itemLayoutId;
        this.itemBrId = itemBrId;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, itemLayoutId, parent, false);
        SimpleViewHolder viewHolder = new SimpleViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        handleViewHolder(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.getBinding().setVariable(itemBrId, mDatas.get(position));
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, holder.getBinding().getRoot(), position, getItemId(position));
                }
            }
        });
        holder.getBinding().getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    return onItemLongClickListener.onItemLongClick(null, holder.getBinding().getRoot(), position, getItemId(position));
                }
                return false;
            }
        });
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 处理item界面元素
     *
     * @param viewHolder
     */
    protected void handleViewHolder(SimpleViewHolder viewHolder) {

    }
}