package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.ContactsContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.viewmodel.ContactsViewModel;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsPresenter implements ContactsContract.Presenter {

    private final ContactsContract.Ui mUi;
    private ContactsViewModel contactsViewModel;

    public ContactsPresenter(ContactsContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public void start() {
        // 开始异步加载
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<Contact> list = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().list();

                contactsViewModel = new ContactsViewModel(formatData(list));

                //数据加载完成 显示UI
                mUi.showResult();
            }
        });
    }

    public void updateContact(){
        //TODO 更新item
    }

    private List<ContactItemViewModel> formatData(List<Contact> list) {

        List<ContactItemViewModel> result = new ArrayList<>();

        ContactItemViewModel item;

        if (list == null)
            return result;

        for (Contact contact :
                list) {
            //注意 phoneList 存储的数据类型为 String
            List<String> phoneList = new ArrayList<>();
            List<Phone> phoneListSource = contact.getPhoneList();
            if (phoneListSource.size() != 0)
                for (int i = 0; i < phoneListSource.size(); i++) {
                    phoneList.add(phoneListSource.get(i).getPhone());
                }

            item = new ContactItemViewModel(contact.getContact_id(),
                    contact.getIcon(),
                    contact.getName(),
                    contact.getFirstPingYin(),
                    phoneList,
                    contact.getOrganization(),
                    contact.getJob());

            result.add(item);
        }

        return result;
    }

    @Override
    public ContactsViewModel getContactsViewModel(){
        return contactsViewModel;
    }
}
