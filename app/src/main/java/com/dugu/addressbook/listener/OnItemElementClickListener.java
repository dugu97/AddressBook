package com.dugu.addressbook.listener;

/**
 * adapter的监听器
 */

public interface OnItemElementClickListener<T> {
    void onClick(T obj, int position);
}
