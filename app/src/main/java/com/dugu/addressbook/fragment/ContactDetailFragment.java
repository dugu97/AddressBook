package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.databinding.FragContactDetailBinding;

public class ContactDetailFragment extends BaseFragment{

    FragContactDetailBinding binding;

    public static ContactDetailFragment newInstance(Bundle bundle) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_contact_detail, container, false);
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
}
