package com.dugu.addressbook.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.ContactDetailFragment;
import com.dugu.addressbook.presenter.ContactDetailPresenter;

public class ContactDetailActivity extends BaseActivityNoBar {

    private ContactDetailPresenter presenter;

    protected Fragment createFragment(Bundle bundle) {
        ContactDetailFragment fragment = ContactDetailFragment.newInstance(bundle);
        //创建Presenter
        presenter = new ContactDetailPresenter(fragment);
        return fragment;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_layout_no_toolbar_single_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment mFragment = fm.findFragmentById(R.id.container);
        if (mFragment == null) {
            mFragment = createFragment(bundle);
            fm.beginTransaction().add(R.id.container, mFragment).commit();
        }

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
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
