package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.ShareContactChooseFragment;
import com.dugu.addressbook.presenter.ShareContactChoosePresenter;

public class ShareContactChooseActivity extends SingleFragmentActivity {

    private ShareContactChoosePresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        ShareContactChooseFragment fragment = ShareContactChooseFragment.newInstance(bundle);
        //创建Presenter
        presenter = new ShareContactChoosePresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().getToolbar().inflateMenu(R.menu.navi_confirm_icon);
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_cancle_icon_);
        getToolBar().setCenterTitleText("选择需分享的成员");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
