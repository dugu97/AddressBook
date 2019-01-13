package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.ContactChooseFragment;
import com.dugu.addressbook.presenter.ContactChoosePresenter;

public class ContactChooseActivity extends SingleFragmentActivity{

    private ContactChoosePresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        ContactChooseFragment fragment = ContactChooseFragment.newInstance(bundle);
        //创建Presenter
        presenter = new ContactChoosePresenter(fragment);
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
