package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.SearchContactViewModel;

public interface SearchContactContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        void searchContact(String key);
        SearchContactViewModel getSearchContactViewModel();
    }
}
