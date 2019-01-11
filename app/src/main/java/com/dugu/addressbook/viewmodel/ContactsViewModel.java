package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.List;

public class ContactsViewModel extends BindingItem{

    private List<ContactItemViewModel> contacts;

    public ContactsViewModel(List<ContactItemViewModel> contacts) {
        this.contacts = contacts;

        //添加工具栏
        addUtilItem();
    }

    private void addUtilItem(){

        Contact contact = new Contact();
        contact.setContact_id((long) Constants.UTIL_GROUP_INDEX);
        contact.setName("群组");
        //添加工具栏
        contacts.add(new ContactItemViewModel(contact));

        Contact contact1 = new Contact();
        contact1.setContact_id((long) Constants.UTIL_BUSINESS_CARD_INDEX);
        contact1.setName("名片夹");

        contacts.add(new ContactItemViewModel(contact1));
    }

    public List<ContactItemViewModel> getContacts() {
        return contacts;
    }

    public int getContactsSizeWithoutOtherUtilItem(){
        return contacts.size() - 2;
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
