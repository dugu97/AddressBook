package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dugu.addressbook.R;
import com.dugu.addressbook.fragment.ImportAndExportFragment;
import com.dugu.addressbook.presenter.ImportAndExportPresenter;

public class ImportAndExportActivity extends SingleFragmentActivity {

    private ImportAndExportPresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        ImportAndExportFragment fragment = ImportAndExportFragment.newInstance(bundle);
        //创建Presenter
        presenter = new ImportAndExportPresenter(fragment);
        return fragment;
    }

    @Override
    protected void initViews() {
        getToolBar().setTitleText("导入/导出联系人");
        getToolBar().setNavigationIcon(R.drawable.vector_drawable_left_arrow);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }
}
