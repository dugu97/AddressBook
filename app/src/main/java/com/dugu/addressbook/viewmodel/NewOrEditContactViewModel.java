package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;

public class NewOrEditContactViewModel extends BindingItem {



    @Override
    public int getViewType() {
        return BR.newOrEditContact;
    }

    @Override
    public int getViewVariableId() {
        return R.layout.frag_edit_and_new_contact;
    }
}
