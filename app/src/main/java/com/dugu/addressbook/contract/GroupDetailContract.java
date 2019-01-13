package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.GroupDetailViewModel;

public interface GroupDetailContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        void renameGroup(String newName);
        GroupDetailViewModel getGroupDetailViewModel();
        void setGroupId(Long groupId);
    }
}
