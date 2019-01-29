package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.ScanFragment;

public class ScanActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(Bundle bundle) {
        ScanFragment fragment = ScanFragment.newInstance(bundle);
        //创建Presenter
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().setTitleText("扫描二维码");
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_left_arrow);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
