package com.dugu.addressbook.adapter.recycleview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dugu.addressbook.viewmodel.BindingItem;

import java.util.List;

/**
 * recucleview的不同item绑定adapter (基于SortedList)
 */

public class SortedListAdapter<T extends BindingItem, TD extends ViewDataBinding> extends RecyclerView.Adapter<SimpleViewHolder<TD>> {

    protected SortedList<T> sortedList;

    protected AdapterView.OnItemClickListener onItemClickListener;

    protected AdapterView.OnItemLongClickListener onItemLongClickListener;

    public SortedListAdapter() {
    }

    public SortedListAdapter(SortedList<T> sortedList) {
        this.sortedList = sortedList;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TD binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        SimpleViewHolder viewHolder = new SimpleViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final SimpleViewHolder<TD> holder, final int position) {
        holder.getBinding().setVariable(sortedList.get(position).getViewVariableId(), sortedList.get(position));
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
        handleViewHolder(holder, holder.getBinding(), sortedList.get(position), position);
        holder.getBinding().executePendingBindings();
    }

    public void setSortedList(SortedList sortedList){
        this.sortedList = sortedList;
    }

    public void setData(List<T> mData){
        sortedList.beginBatchedUpdates();
        sortedList.addAll(mData);
        sortedList.endBatchedUpdates();
        notifyDataSetChanged();
    }

    public void addData(T item){
        sortedList.add(item);
        notifyDataSetChanged();
    }

    public void removeData(int position){
        sortedList.removeItemAt(position);
        notifyDataSetChanged();
    }

    public void clear(){
        sortedList.clear();
    }

    @Override
    public int getItemCount() {
        return sortedList == null ? 0 : sortedList.size();
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

    @Override
    public int getItemViewType(int position) {
        return sortedList.get(position).getViewType();
    }

    /**
     * 处理item界面元素
     *
     * @param binding
     * @param position
     */
    protected void handleViewHolder(TD binding, T obj, int position) {

    }

    /**
     * 处理item界面元素
     *
     * @param holder
     * @param binding
     * @param obj
     * @param position
     */
    protected void handleViewHolder(SimpleViewHolder<TD> holder, TD binding, T obj, int position) {
        handleViewHolder(holder.getBinding(), sortedList.get(position), position);
    }
}
