package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.GroupDetailContract;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.GroupDao;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.GroupDetailViewModel;
import com.dugu.addressbook.viewmodel.item.GroupDetailItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupDetailPresenter implements GroupDetailContract.Presenter {

    private final GroupDetailContract.Ui mUi;

    private GroupDetailViewModel groupDetailViewModel;
    private Long group_id;

    public GroupDetailPresenter(GroupDetailContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public GroupDetailViewModel getGroupDetailViewModel() {
        return groupDetailViewModel;
    }

    @Override
    public void setGroupId(Long groupId) {
        this.group_id = groupId;
    }

    @Override
    public void start() {
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                DaoSession daoSession = AddressBookApplication.getDaoSession();
                Group group = daoSession.getGroupDao().queryBuilder().where(GroupDao.Properties.Group_id.eq(group_id)).unique();

                List<GroupDetailItemViewModel> viewModels = new ArrayList<>();
                for (Contact contact :
                        group.getContactList()) {
                    viewModels.add(new GroupDetailItemViewModel(contact));
                }
                groupDetailViewModel = new GroupDetailViewModel(group,viewModels);

                mUi.showResult();
            }
        });
    }
}
