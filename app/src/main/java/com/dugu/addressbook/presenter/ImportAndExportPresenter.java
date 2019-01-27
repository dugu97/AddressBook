package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.ImportAndExportContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.util.MobileContactUtil;
import com.dugu.addressbook.viewmodel.ImportAndExportViewModel;

import java.util.List;

public class ImportAndExportPresenter implements ImportAndExportContract.Presenter {

    private final ImportAndExportContract.Ui mUi;

    private ImportAndExportViewModel importAndExportViewModel;

    public ImportAndExportPresenter(ImportAndExportContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public ImportAndExportViewModel getImportAndExportViewModel() {
        return importAndExportViewModel;
    }

    @Override
    public void start() {
        List<Contact> contactList = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().list();
        importAndExportViewModel = new ImportAndExportViewModel(contactList);
        mUi.showResult();
    }

    @Override
    public void importContact(List<ContactWithPhoneAndEmail> models) {
        MobileContactUtil.importContactWithList(models);
    }
}
