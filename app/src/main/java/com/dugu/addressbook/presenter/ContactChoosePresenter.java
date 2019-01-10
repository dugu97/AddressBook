package com.dugu.addressbook.presenter;

import com.dugu.addressbook.contract.ContactChooseContract;
import com.dugu.addressbook.viewmodel.ContactChooseViewModel;

public class ContactChoosePresenter implements ContactChooseContract.Presenter {

    private final ContactChooseContract.Ui mUi;

    private ContactChooseViewModel contactChooseViewModel;

    public ContactChoosePresenter(ContactChooseContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public ContactChooseViewModel getContactChooseViewModel() {
        return contactChooseViewModel;
    }

    @Override
    public void start() {

    }
}
