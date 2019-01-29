package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.databinding.FragArrangeContactBinding;

public class ArrangeContactFragment extends BaseFragment {

    FragArrangeContactBinding binding;

    public static ArrangeContactFragment newInstance(Bundle bundle) {
        ArrangeContactFragment fragment = new ArrangeContactFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_arrange_contact, container, false);
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
        binding.mergeContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.imperfectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.chooseContactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
