package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.NewOrEditContactContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.viewmodel.NewOrEditContactViewModel;

public class NewOrEditContactPresenter implements NewOrEditContactContract.Presenter {

    private final NewOrEditContactContract.Ui mUi;

    private Contact contact;

    public NewOrEditContactPresenter(NewOrEditContactContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public NewOrEditContactViewModel getContact() {
        return null;
    }

    @Override
    public void createContact(NewOrEditContactViewModel contact) {

    }

    @Override
    public void start() {
        // 异步加载单个联系人（Edit的情况）
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
