package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;

public class GroupDetailViewModel extends BindingItem {


    @Override
    public int getViewType() {
        return R.layout.frag_group_detail;
    }

    @Override
    public int getViewVariableId() {
        return BR.GroupDetailViewModel;
    }
}
