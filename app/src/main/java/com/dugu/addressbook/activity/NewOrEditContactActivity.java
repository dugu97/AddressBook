package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.NewOrEditContactFragment;
import com.dugu.addressbook.presenter.NewOrEditContactPresenter;

public class NewOrEditContactActivity extends SingleFragmentActivity {

    private NewOrEditContactPresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        NewOrEditContactFragment fragment = NewOrEditContactFragment.newInstance(bundle);
        //创建Presenter
        presenter = new NewOrEditContactPresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().getToolbar().inflateMenu(R.menu.navi_confirm_icon);
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_cancle_icon_);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
