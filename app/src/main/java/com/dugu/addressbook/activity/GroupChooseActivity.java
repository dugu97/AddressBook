package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.GroupChooseFragment;
import com.dugu.addressbook.presenter.GroupChoosePresenter;

public class GroupChooseActivity extends SingleFragmentActivity{

    private GroupChoosePresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        GroupChooseFragment fragment = GroupChooseFragment.newInstance(bundle);
        //创建Presenter
        presenter = new GroupChoosePresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().getToolbar().inflateMenu(R.menu.navi_confirm_icon);
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_cancle_icon_);
        getToolBar().setCenterTitleText("选择群组");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
