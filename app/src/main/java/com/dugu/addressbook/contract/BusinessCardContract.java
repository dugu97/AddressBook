package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.BusinessCardViewModel;

public interface BusinessCardContract {
    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        BusinessCardViewModel getBusinessCardViewModel();
    }
}
