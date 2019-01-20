package com.dugu.addressbook.contract;

import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.viewmodel.ImportAndExportViewModel;

import java.util.List;

public interface ImportAndExportContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
    }

    interface Presenter extends BasePresenter{
        ImportAndExportViewModel getImportAndExportViewModel();
        void importContact(List<ContactWithPhoneAndEmail> models);
    }

}
