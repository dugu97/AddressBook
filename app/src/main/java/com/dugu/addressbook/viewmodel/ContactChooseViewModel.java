package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;

public class ContactChooseViewModel extends BindingItem {
    @Override
    public int getViewType() {
        return R.layout.frag_contact_choose;
    }

    @Override
    public int getViewVariableId() {
        return BR.ContactChooseViewModel;
    }
}
