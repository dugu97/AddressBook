package com.dugu.addressbook.presenter;

import android.util.Log;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.LinkmanContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.viewmodel.ContactItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsPresenter implements LinkmanContract.Presenter {

    private final LinkmanContract.Ui mUi;
    private List<Contact> list;

    public ContactsPresenter(LinkmanContract.Ui mUi) {
        this.mUi = mUi;

        // 异步加载
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                list = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().list();
            }
        });

        mUi.setPresenter(this);
    }

    @Override
    public void start() {
        mUi.showResult();
    }

    @Override
    public List<ContactItemViewModel> getAllContact() {
        return formatData(list);
    }

    private List<ContactItemViewModel> formatData(List<Contact> list) {

        List<ContactItemViewModel> result = new ArrayList<>();
        ContactItemViewModel item;

        for (Contact contact :
                list) {

            //phoneList不为null，只需要第一个phone
            String phone = "";
            List<Phone> phoneList = contact.getPhoneList();
            if (phoneList.size() != 0)
                phone = phoneList.get(0).getPhone();

            Log.d("123", phone);

            item = new ContactItemViewModel(contact.getContact_id(),
                    contact.getIcon(),
                    contact.getName(),
                    phone,
                    contact.getOrganization(),
                    contact.getJob());

            result.add(item);
        }


        //添加列表顶固定选项
        item = new ContactItemViewModel((long) -9, null, "群组", null, null, null, "#");
        result.add(item);
        item = new ContactItemViewModel((long) -5, null, "名片夹", null, null, null, "#");
        result.add(item);

        return result;
    }
}
