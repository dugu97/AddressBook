package com.dugu.addressbook.presenter;

import android.util.Log;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.GroupContract;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.GroupLinkContactDao;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.GroupViewModel;
import com.dugu.addressbook.viewmodel.item.GroupItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupPresenter implements GroupContract.Presenter {

    private final GroupContract.Ui mUi;

    private GroupViewModel groupViewModel;
    private List<Group> deleteGroup;

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
        List<GroupItemViewModel> viewModels = new ArrayList<>();
        AddressBookApplication.getDaoSession().getGroupDao().queryBuilder().list().clear();
        List<Group> groupList = AddressBookApplication.getDaoSession().getGroupDao().queryBuilder().list();
        Log.d("123", groupList.size() + "个群组");
        for (int i = 0; i < groupList.size(); i++) {
            Group g = groupList.get(i);
            if (g.getGroup_id() > Constants.GROUP_BLACK) {
                Long num = AddressBookApplication.getDaoSession().getGroupLinkContactDao().queryBuilder()
                        .where(GroupLinkContactDao.Properties.Group_id.eq(g.getGroup_id())).count();
                viewModels.add(new GroupItemViewModel(g, num));
            }
        }
        groupViewModel = new GroupViewModel(viewModels);
        mUi.showResult();
    }


    @Override
    public void setDeleteGroup(List<Group> deleteGroup) {
        this.deleteGroup = deleteGroup;
    }

    @Override
    public void deleteGroup() {
        DaoSession daoSession = AddressBookApplication.getDaoSession();
        for (int i = 0; i < deleteGroup.size(); i++) {
            daoSession.getGroupLinkContactDao().deleteInTx(
                    daoSession.getGroupLinkContactDao().queryBuilder()
                            .where(GroupLinkContactDao.Properties.Group_id.eq(deleteGroup.get(i).getGroup_id())).list());
            daoSession.getGroupDao().deleteByKey(deleteGroup.get(i).getGroup_id());
        }
        //清除缓存
        daoSession.clear();
    }

    @Override
    public void createGroup(final String group_name) {
        Long group_id = AddressBookApplication.getDaoSession().getGroupDao().insert(new Group(null, group_name));
        Log.d("123", group_id + "插入的id");
        Group g = new Group(group_id, group_name);
        GroupItemViewModel viewModel = new GroupItemViewModel(g, (long) 0);
        groupViewModel.getGroupItemViewModels().add(viewModel);
        mUi.addGroup();
    }
}
