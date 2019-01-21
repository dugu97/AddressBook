package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.MainFragment;
import com.dugu.addressbook.presenter.ContactsPresenter;

public class MainActivity extends SingleFragmentActivity {

    private ContactsPresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        MainFragment fragment = MainFragment.newInstance(bundle);
        //创建Presenter
        presenter = new ContactsPresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().setCenterTitleVisiblity(View.GONE);
        getToolBar().setTitleText("通讯录");
        getToolBar().getToolbar().inflateMenu(R.menu.navi_search_icon);
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
