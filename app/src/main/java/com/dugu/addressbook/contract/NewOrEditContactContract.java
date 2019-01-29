package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.NewOrEditContactViewModel;

public interface NewOrEditContactContract {
    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        NewOrEditContactViewModel getNewOrEditContactViewModel();
        void createViewModel(int mode, Long contact_id);
        void createViewModelWithQrCode(int mode, String contactWithPhoneAndEmail);
        void createOrUpdateContact(NewOrEditContactViewModel viewModel);
    }
}
