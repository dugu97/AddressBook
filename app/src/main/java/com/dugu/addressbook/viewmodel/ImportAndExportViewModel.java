package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.model.Contact;

import java.util.List;

public class ImportAndExportViewModel extends BindingItem {

    private List<Contact> contacts;

    public ImportAndExportViewModel(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public int getViewVariableId() {
        return 0;
    }
}
