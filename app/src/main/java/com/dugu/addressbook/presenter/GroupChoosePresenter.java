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
                    String name = g.getGroup_name();
                    if (name.equals(Constants.GROUP_PROJECT[ Constants.GROUP_PHONE])
                            || name.equals(Constants.GROUP_PROJECT[ Constants.GROUP_CARD])){
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
