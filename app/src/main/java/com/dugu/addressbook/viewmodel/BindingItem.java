package com.dugu.addressbook.viewmodel;

import android.databinding.BaseObservable;

/**
 * 绑定数据对象必须继承以下(减少注释使用)
 */

public abstract class BindingItem extends BaseObservable {
    /**
     * 返回布局id
     *
     * @return
     */
    public abstract int getViewType();

    /**
     * 返回对象id
     *
     * @return
     */
    public abstract int getViewVariableId();

}
