package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.List;

public class ContactsViewModel extends BindingItem{

    private List<ContactItemViewModel> contacts;

    public ContactsViewModel(List<ContactItemViewModel> contacts) {
        this.contacts = contacts;
    }

    public List<ContactItemViewModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactItemViewModel> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_main_layout;
    }

    @Override
    public int getViewVariableId() {
        return BR.ContactsViewModel;
    }
}
