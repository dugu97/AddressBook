package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.fragment.GroupFragment;
import com.dugu.addressbook.presenter.GroupPresenter;

public class GroupActivity extends SingleFragmentActivity {

    private GroupPresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        GroupFragment fragment = GroupFragment.newInstance(bundle);
        //创建Presenter
        presenter = new GroupPresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().setTitleText("群组");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
