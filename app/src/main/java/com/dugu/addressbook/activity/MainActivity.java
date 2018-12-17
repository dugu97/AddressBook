package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dugu.addressbook.fragment.MainFragment;
import com.dugu.addressbook.presenter.ContactsPresenter;

public class MainActivity extends SingleFragmentActivity{

    private ContactsPresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        MainFragment fragment = MainFragment.newInstance(bundle);
        //创建Presenter
        presenter = new ContactsPresenter(fragment);
        return fragment;
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    protected void initViews() {
//        getABActionBar().setVisibility(View.GONE);
        getABActionBar().setRightButtonVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected int setDuguActionBarId() {
        return super.setDuguActionBarId();
    }
}
