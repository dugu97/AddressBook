package com.dugu.addressbook.presenter;

import com.dugu.addressbook.contract.GroupContract;
import com.dugu.addressbook.viewmodel.GroupViewModel;

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

    }
}
