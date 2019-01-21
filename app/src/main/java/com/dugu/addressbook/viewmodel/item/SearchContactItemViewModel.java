package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.BindingItem;

public class SearchContactItemViewModel extends BindingItem {

    private Contact contact;

    public SearchContactItemViewModel(Contact contact) {
        this.contact = contact;
    }

    public String getNameOrPhone() {
        if (!AppUtil.isNullString(contact.getName()))
            return contact.getName();
        else if (!contact.getPhoneList().isEmpty())
            return contact.getPhoneList().get(0).getPhone();
        else
            return "(无姓名)";
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public int getViewType() {
        return R.layout.item_search_contact;
    }

    @Override
    public int getViewVariableId() {
        return BR.SearchContactItemViewModel;
    }
}
