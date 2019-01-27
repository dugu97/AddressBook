package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.GroupContactChooseViewModel;
import com.dugu.addressbook.viewmodel.item.GroupContactChooseItemViewModel;

import java.util.List;

public interface GroupContactChooseContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        void createContactChooseViewModel(Long group_id, int mode);
        void insertOrDeleteContactsToGroup(List<GroupContactChooseItemViewModel> viewModels, int mode);
        GroupContactChooseViewModel getContactChooseViewModel();
    }
}
