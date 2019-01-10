package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.contract.GroupDetailContract;
import com.dugu.addressbook.databinding.FragGroupDetailBinding;

public class GroupDetailFragment extends BaseFragment implements GroupDetailContract.Ui {

    private GroupDetailContract.Presenter presenter;
    private FragGroupDetailBinding binding;

    public static GroupDetailFragment newInstance(Bundle bundle) {
        GroupDetailFragment fragment = new GroupDetailFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_group_detail, container, false);
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
    public void setPresenter(GroupDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
