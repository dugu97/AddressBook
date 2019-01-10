package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.contract.GroupContract;
import com.dugu.addressbook.databinding.FragGroupBinding;

public class GroupFragment extends BaseFragment implements GroupContract.Ui {

    private GroupContract.Presenter presenter;
    private FragGroupBinding binding;

    public static GroupFragment newInstance(Bundle bundle) {
        GroupFragment fragment = new GroupFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_group, container, false);
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
    public void setPresenter(GroupContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
