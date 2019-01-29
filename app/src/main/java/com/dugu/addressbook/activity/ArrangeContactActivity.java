package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.ArrangeContactFragment;

public class ArrangeContactActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(Bundle bundle) {
        ArrangeContactFragment fragment = ArrangeContactFragment.newInstance(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().setTitleText("整理联系人");
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_left_arrow);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
