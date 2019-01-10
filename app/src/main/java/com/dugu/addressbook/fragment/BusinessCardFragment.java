package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.contract.BusinessCardContract;
import com.dugu.addressbook.databinding.FragBusinessCardBinding;

public class BusinessCardFragment extends BaseFragment implements BusinessCardContract.Ui{

    private BusinessCardContract.Presenter presenter;
    private FragBusinessCardBinding binding;

    public static BusinessCardFragment newInstance(Bundle bundle) {
        BusinessCardFragment fragment = new BusinessCardFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_business_card, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void initData() {
        presenter.start();
    }

    @Override
    protected void addListener() {

    }

    @Override
    public void showResult() {

    }

    @Override
    public void setPresenter(BusinessCardContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
