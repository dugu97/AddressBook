package com.dugu.addressbook.presenter;

import com.dugu.addressbook.contract.ImportAndExportContract;
import com.dugu.addressbook.viewmodel.ImportAndExportViewModel;

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

    }
}
