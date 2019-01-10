package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;

public class GroupViewModel extends BindingItem {
    @Override
    public int getViewType() {
        return R.layout.frag_group;
    }

    @Override
    public int getViewVariableId() {
        return BR.GroupViewModel;
    }
}
