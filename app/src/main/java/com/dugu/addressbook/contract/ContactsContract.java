package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.ContactsViewModel;

public interface ContactsContract {
    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        ContactsViewModel getContactsViewModel();
    }
}
