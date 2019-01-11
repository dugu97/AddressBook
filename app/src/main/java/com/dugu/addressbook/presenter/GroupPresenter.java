package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.GroupContract;
import com.dugu.addressbook.db.GroupLinkContactDao;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.GroupViewModel;
import com.dugu.addressbook.viewmodel.item.GroupItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupPresenter implements GroupContract.Presenter {

    private final GroupContract.Ui mUi;

    private GroupViewModel groupViewModel;

    public GroupPresenter(GroupContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public GroupViewModel getGroupViewModel() {
        return groupViewModel;
    }

    @Override
    public void start() {
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<GroupItemViewModel> viewModels = new ArrayList<>();
                List<Group> groupList = AddressBookApplication.getDaoSession().getGroupDao().queryBuilder().list();
                for (int i = 0; i < groupList.size(); i++) {
                    Group g = groupList.get(i);
                    if (g.getGroup_id() > Constants.GROUP_BLACK) {
                        Long num = AddressBookApplication.getDaoSession().getGroupLinkContactDao().queryBuilder()
                                .where(GroupLinkContactDao.Properties.Group_id.eq(g.getGroup_id())).count();
                        viewModels.add(new GroupItemViewModel(g,num));
                    }
                }
                groupViewModel = new GroupViewModel(viewModels);
                mUi.showResult();
            }
        });
    }
}
