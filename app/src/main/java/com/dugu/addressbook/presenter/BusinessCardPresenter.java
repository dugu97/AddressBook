package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.BusinessCardContract;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.GroupDao;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Group;
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

        DaoSession daoSession = AddressBookApplication.getDaoSession();
        Group group = daoSession.getGroupDao().queryBuilder().where(GroupDao.Properties.Group_id.eq(Constants.GROUP_CARD)).unique();

        List<ContactItemViewModel> viewModels = new ArrayList<>();
        for (Contact contact : group.getContactList()) {
            viewModels.add(new ContactItemViewModel(contact));
        }

        businessCardViewModel = new BusinessCardViewModel(viewModels);

        //数据加载完成 显示UI
        mUi.showResult();
    }
}
