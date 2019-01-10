package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.fragment.GroupDetailFragment;
import com.dugu.addressbook.fragment.GroupFragment;
import com.dugu.addressbook.presenter.GroupDetailPresenter;
import com.dugu.addressbook.presenter.GroupPresenter;

public class GroupDetailActivity extends SingleFragmentActivity{


    private GroupDetailPresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        GroupDetailFragment fragment = GroupDetailFragment.newInstance(bundle);
        //创建Presenter
        presenter = new GroupDetailPresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
