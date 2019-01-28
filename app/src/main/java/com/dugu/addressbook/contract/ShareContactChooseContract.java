package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.ShareContactChooseViewModel;

public interface ShareContactChooseContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        ShareContactChooseViewModel getShareContactChooseViewModel();
    }
}
