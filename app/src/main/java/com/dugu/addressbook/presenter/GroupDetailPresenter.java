package com.dugu.addressbook.presenter;

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

    }
}
