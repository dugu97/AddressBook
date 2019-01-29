package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.BindingItem;

public class ContactChooseItemViewModel extends BindingItem {

    private Contact contact;
    private boolean isChecked;

    public ContactChooseItemViewModel(Contact contact, boolean isChecked) {
        this.contact = contact;
        this.isChecked = isChecked;
    }

    public String getNameOrPhone() {
        return AppUtil.getContactName(contact);
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int getViewType() {
        return R.layout.item_contact_choose;
    }

    @Override
    public int getViewVariableId() {
        return BR.ContactChooseItemViewModel;
    }
}
