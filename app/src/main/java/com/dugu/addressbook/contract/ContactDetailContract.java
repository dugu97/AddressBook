package com.dugu.addressbook.contract;


import com.dugu.addressbook.viewmodel.ContactDetailViewModel;
import com.dugu.addressbook.viewmodel.item.ContactDetailItemViewModel;

import java.util.List;

public interface ContactDetailContract {
    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        void createContactDetailViewModel(Long contact_id);
        ContactDetailViewModel getContactDetailViewModel();
        List<ContactDetailItemViewModel> getAllMessageItems();
    }
}
