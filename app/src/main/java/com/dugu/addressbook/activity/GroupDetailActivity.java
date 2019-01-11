package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.GroupDetailFragment;
import com.dugu.addressbook.presenter.GroupDetailPresenter;

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
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_left_arrow);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
