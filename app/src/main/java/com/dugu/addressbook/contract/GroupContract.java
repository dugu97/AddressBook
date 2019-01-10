package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.GroupViewModel;

public interface GroupContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        GroupViewModel getGroupViewModel();
    }
}
