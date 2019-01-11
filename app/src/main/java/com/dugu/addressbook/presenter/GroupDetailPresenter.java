package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.GroupDetailContract;
import com.dugu.addressbook.viewmodel.GroupDetailViewModel;

public class GroupDetailPresenter implements GroupDetailContract.Presenter {

    private final GroupDetailContract.Ui mUi;

    private GroupDetailViewModel groupDetailViewModel;

    public GroupDetailPresenter(GroupDetailContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public GroupDetailViewModel getGroupDetailViewModel() {
        return groupDetailViewModel;
    }

    @Override
    public void start() {
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
//                List<GroupDetailItemViewModel> viewModels = new ArrayList<>();
//                List<Group> groupList = AddressBookApplication.getDaoSession().getGroupDao().queryBuilder().list();
//                for (int i = 0; i < groupList.size(); i++) {
//                    Group g = groupList.get(i);
//                    if (g.getGroup_id() > Constants.GROUP_BLACK) {
//                        Long num = AddressBookApplication.getDaoSession().getGroupLinkContactDao().queryBuilder()
//                                .where(GroupLinkContactDao.Properties.Group_id.eq(g.getGroup_id())).count();
//                        viewModels.add(new GroupItemViewModel(g, num));
//                    }
//                }
//                groupViewModel = new GroupViewModel(viewModels);
//                mUi.showResult();
            }
        });
    }
}
