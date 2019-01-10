package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.contract.ContactChooseContract;
import com.dugu.addressbook.databinding.FragContactChooseBinding;

public class ContactChooseFragment extends BaseFragment implements ContactChooseContract.Ui {

    private ContactChooseContract.Presenter presenter;
    private FragContactChooseBinding binding;

    public static ContactChooseFragment newInstance(Bundle bundle) {
        ContactChooseFragment fragment = new ContactChooseFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_contact_choose, container, false);
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
    public void setPresenter(ContactChooseContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
