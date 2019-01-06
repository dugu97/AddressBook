package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.GroupChooseViewModel;

public interface GroupChooseContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        GroupChooseViewModel getGroupChooseViewModel();
        void createViewModel();
    }
}
