package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.ContactChooseViewModel;

public interface ContactChooseContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        ContactChooseViewModel getContactChooseViewModel();
    }
}
