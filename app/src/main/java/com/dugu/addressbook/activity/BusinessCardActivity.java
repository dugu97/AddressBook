package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.fragment.BusinessCardFragment;
import com.dugu.addressbook.presenter.BusinessCardPresenter;

public class BusinessCardActivity extends SingleFragmentActivity{

    private BusinessCardPresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        BusinessCardFragment fragment = BusinessCardFragment.newInstance(bundle);
        //创建Presenter
        presenter = new BusinessCardPresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().setTitleText("名片夹");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
