package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.ContactsContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.viewmodel.ContactItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsPresenter implements ContactsContract.Presenter {

    private final ContactsContract.Ui mUi;
    private List<Contact> list;

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
                list = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().list();

                //加载完成显示UI
                mUi.showResult();
            }
        });
    }

    @Override
    public List<ContactItemViewModel> getAllContact() {
        return formatData(list);
    }

    private List<ContactItemViewModel> formatData(List<Contact> list) {

        List<ContactItemViewModel> result = new ArrayList<>();

        if (list == null)
            return result;

        ContactItemViewModel item;

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
                    contact.getGroup_id(),
                    contact.getIcon(),
                    contact.getName(),
                    phoneList,
                    contact.getOrganization(),
                    contact.getJob());

            result.add(item);
        }


        //添加列表顶固定选项
        item = new ContactItemViewModel((long) -9, "群组", "#");
        result.add(item);
        item = new ContactItemViewModel((long) -5, "名片夹", "#");
        result.add(item);

        return result;
    }
}
