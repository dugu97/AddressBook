package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.GroupViewModel;

public interface GroupContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
        void addGroup();
    }

    interface Presenter extends BasePresenter{
        void createGroup(String group_name);
        GroupViewModel getGroupViewModel();
    }
}
