package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.ShareContactChooseContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.viewmodel.ShareContactChooseViewModel;
import com.dugu.addressbook.viewmodel.item.ContactChooseItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShareContactChoosePresenter implements ShareContactChooseContract.Presenter {


    private final ShareContactChooseContract.Ui mUi;

    private ShareContactChooseViewModel shareContactChooseViewModel;

    public ShareContactChoosePresenter(ShareContactChooseContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public ShareContactChooseViewModel getShareContactChooseViewModel() {
        return shareContactChooseViewModel;
    }

    @Override
    public void start() {
        List<Contact> list = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().list();

        List<ContactChooseItemViewModel> viewModels = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            viewModels.add(new ContactChooseItemViewModel(list.get(i), false));
        }

        shareContactChooseViewModel = new ShareContactChooseViewModel(viewModels);

        //数据加载完成 显示UI
        mUi.showResult();
    }
}
