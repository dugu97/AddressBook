package com.dugu.addressbook.presenter;

import com.dugu.addressbook.contract.BusinessCardContract;
import com.dugu.addressbook.viewmodel.BusinessCardViewModel;

public class BusinessCardPresenter implements BusinessCardContract.Presenter {

    private final BusinessCardContract.Ui mUi;

    private BusinessCardViewModel businessCardViewModel;

    public BusinessCardPresenter(BusinessCardContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public BusinessCardViewModel getBusinessCardViewModel() {
        return businessCardViewModel;
    }

    @Override
    public void start() {

    }
}
