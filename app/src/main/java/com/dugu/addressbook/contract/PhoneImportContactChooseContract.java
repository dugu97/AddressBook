package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.item.PhoneImportContactChooseItemViewModel;

import java.util.List;

public interface PhoneImportContactChooseContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        void importContact(List<PhoneImportContactChooseItemViewModel> models);
    }

}
