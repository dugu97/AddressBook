package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.ShowImageFragment;

public class ShowImageActivty extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(Bundle bundle) {
        ShowImageFragment fragment = ShowImageFragment.newInstance(bundle);
        //创建Presenter
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().setTitleText("名片详情");
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_left_arrow);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
