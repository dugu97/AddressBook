package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.PhoneImportContactChooseFragment;
import com.dugu.addressbook.presenter.PhoneImportContactChoosePresenter;

public class PhoneImportContactChooseActivity extends SingleFragmentActivity {

    private PhoneImportContactChoosePresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        PhoneImportContactChooseFragment fragment = PhoneImportContactChooseFragment.newInstance(bundle);
        //创建Presenter
        presenter = new PhoneImportContactChoosePresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().getToolbar().inflateMenu(R.menu.navi_confirm_icon);
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_cancle_icon_);
        getToolBar().setCenterTitleText("选择要导入的联系人");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
