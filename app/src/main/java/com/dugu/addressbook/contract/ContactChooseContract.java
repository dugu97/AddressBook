package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.ContactChooseViewModel;
import com.dugu.addressbook.viewmodel.item.ContactChooseItemViewModel;

import java.util.List;

public interface ContactChooseContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        void createContactChooseViewModel(Long group_id, int mode);
        void insertOrDeleteContactsToGroup(List<ContactChooseItemViewModel> viewModels, int mode);
        ContactChooseViewModel getContactChooseViewModel();
    }
}
