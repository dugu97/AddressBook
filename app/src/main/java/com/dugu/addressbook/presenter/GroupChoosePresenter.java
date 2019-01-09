package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.GroupChooseContract;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.GroupChooseViewModel;
import com.dugu.addressbook.viewmodel.item.GroupChooseItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupChoosePresenter implements GroupChooseContract.Presenter {

    private final GroupChooseContract.Ui mUi;

    private GroupChooseViewModel groupChooseViewModel;

    public GroupChoosePresenter(GroupChooseContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public GroupChooseViewModel getGroupChooseViewModel() {
        return groupChooseViewModel;
    }


    @Override
    public void start() {
        // 异步加载群组
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<Group> groupList = AddressBookApplication.getDaoSession().getGroupDao().queryBuilder().list();
                List<GroupChooseItemViewModel> itemViewModels = new ArrayList<>();
                for (Group g : groupList) {
                    if (g.getGroup_id() == Constants.GROUP_PHONE
                            || g.getGroup_id() == Constants.GROUP_CARD
                            || g.getGroup_id() == Constants.GROUP_BLACK) {
                        continue;
                    }
                    itemViewModels.add(new GroupChooseItemViewModel(g,false));
                }
                groupChooseViewModel = new GroupChooseViewModel(itemViewModels);

                mUi.showResult();
            }
        });
    }
}
