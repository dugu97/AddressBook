package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.contract.ImportAndExportContract;
import com.dugu.addressbook.databinding.FragImportAndExportBinding;

public class ImportAndExportFragment extends BaseFragment implements ImportAndExportContract.Ui {

    private ImportAndExportContract.Presenter presenter;
    private FragImportAndExportBinding binding;

    public static ImportAndExportFragment newInstance(Bundle bundle) {
        ImportAndExportFragment fragment = new ImportAndExportFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_import_and_export, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }

    @Override
    public void showResult() {

    }

    @Override
    public void setPresenter(ImportAndExportContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
