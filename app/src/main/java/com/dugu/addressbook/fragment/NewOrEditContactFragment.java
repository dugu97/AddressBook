package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.contract.NewOrEditContactContract;
import com.dugu.addressbook.databinding.FragEditAndNewContactBinding;

public class NewOrEditContactFragment extends BaseFragment implements NewOrEditContactContract.Ui {

    FragEditAndNewContactBinding binding;
    private NewOrEditContactContract.Presenter presenter;

    public static NewOrEditContactFragment newInstance(Bundle bundle) {
        NewOrEditContactFragment fragment = new NewOrEditContactFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_edit_and_new_contact, container, false);
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
    public void setPresenter(NewOrEditContactContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showResult() {

    }
}
