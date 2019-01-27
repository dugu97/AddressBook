package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.ContactsViewModel;

public interface ContactsContract {
    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        void deleteContact(Long contact_id);
        void addContactInBlack(Long contact_id);
        void deleteContactInBlack(Long contact_id);
        ContactsViewModel getContactsViewModel();
    }
}
