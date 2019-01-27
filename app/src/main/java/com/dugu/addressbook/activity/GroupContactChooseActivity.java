package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.GroupContactChooseFragment;
import com.dugu.addressbook.presenter.GroupContactChoosePresenter;

public class GroupContactChooseActivity extends SingleFragmentActivity{

    private GroupContactChoosePresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        GroupContactChooseFragment fragment = GroupContactChooseFragment.newInstance(bundle);
        //创建Presenter
        presenter = new GroupContactChoosePresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().getToolbar().inflateMenu(R.menu.navi_confirm_icon);
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_cancle_icon_);
        getToolBar().setCenterTitleText("选择群组成员");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
