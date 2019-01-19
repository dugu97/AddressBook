package com.dugu.addressbook.contract;

import com.dugu.addressbook.viewmodel.ImportAndExportViewModel;

public interface ImportAndExportContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        ImportAndExportViewModel getImportAndExportViewModel();
    }

}
