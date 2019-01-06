package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.NewOrEditContactContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.viewmodel.NewOrEditContactViewModel;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

import java.util.ArrayList;

public class NewOrEditContactPresenter implements NewOrEditContactContract.Presenter {

    private final NewOrEditContactContract.Ui mUi;

    private NewOrEditContactViewModel newOrEditContactViewModel;

    private Contact contact;

    public NewOrEditContactPresenter(NewOrEditContactContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
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

    @Override
    public NewOrEditContactViewModel getNewOrEditContactViewModel() {
        return newOrEditContactViewModel;
    }

    @Override
    public void createViewModel(int mode, Long contact_id) {
        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT || mode == Constants.CONTACT_MODE_NEW_SMS_CONTACT) {
            newOrEditContactViewModel = new NewOrEditContactViewModel(mode, null, null, null, null, null, new ArrayList<ContactInputItemViewModel>());
        }


    }
}
