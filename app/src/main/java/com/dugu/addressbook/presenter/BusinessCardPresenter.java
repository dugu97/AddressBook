package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.BusinessCardContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.viewmodel.BusinessCardViewModel;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.ArrayList;
import java.util.List;

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
        List<Contact> list = AddressBookApplication.getDaoSession().getContactDao()._queryGroup_ContactList((long) Constants.GROUP_CARD);

        List<ContactItemViewModel> viewModels = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            viewModels.add(new ContactItemViewModel(list.get(i)));
        }

        businessCardViewModel = new BusinessCardViewModel(viewModels);

        //数据加载完成 显示UI
        mUi.showResult();
    }
}
