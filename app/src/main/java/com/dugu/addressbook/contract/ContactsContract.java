package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.ContactItemViewModel;

import java.util.List;

public interface ContactsContract {
    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        List<ContactItemViewModel> getAllContact();
    }
}