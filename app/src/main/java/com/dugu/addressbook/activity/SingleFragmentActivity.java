package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.dugu.addressbook.R;


/**
 * 每个需要bar的activity最好都继承这个类
 */

public abstract class SingleFragmentActivity extends BaseActivity {

    protected abstract Fragment createFragment(Bundle bundle);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_layout_single_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment mFragment = fm.findFragmentById(R.id.container);
        if (mFragment == null) {
            mFragment = createFragment(savedInstanceState);
            fm.beginTransaction().add(R.id.container, mFragment).commit();
        }
    }

    @Override
    protected int setDuguActionBarId() {
        return R.id.ABToolbar;
    }
}
