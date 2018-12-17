package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.ContactSortedListAdapter;
import com.dugu.addressbook.adapter.ContactSortedListCallback;
import com.dugu.addressbook.assembly.SideBar;
import com.dugu.addressbook.contract.LinkmanContract;
import com.dugu.addressbook.databinding.FragMainLayoutBinding;
import com.dugu.addressbook.viewmodel.ContactItemViewModel;

public class MainFragment extends BaseFragment implements LinkmanContract.Ui {

    private LinkmanContract.Presenter presenter;
    private ContactItemViewModel linkManViewModel;
    private ContactSortedListAdapter adapter;

    private int contactCount;

    private SideBar sideBar;

    private FragMainLayoutBinding binding;

    public static MainFragment newInstance(Bundle bundle) {
        MainFragment fragment = new MainFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_main_layout, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        //配置sidebar
        binding.sidebar.setTextView(binding.dialog);

        //配置RecycleView
        binding.contactsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactSortedListAdapter();
        ContactSortedListCallback sortedListCallback = new ContactSortedListCallback(adapter);
        SortedList<ContactItemViewModel> sortedList = new SortedList<>(ContactItemViewModel.class, sortedListCallback);
        adapter.setSortedList(sortedList);
        binding.contactsRecycleView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
//        presenter.start();

        //配置联系人数据

        adapter.setData(presenter.getAllContact());
        contactCount = adapter.getItemCount();
    }

    @Override
    protected void addListener() {
        binding.sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {

            }
        });

        binding.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etSearch.setText("");
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                makeToast(s.toString());
                if ("".equals(s.toString()))
                    binding.deleteText.setVisibility(View.GONE);
                else
                    binding.deleteText.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void showResult() {
        Log.d("123","加载了" + contactCount + "个联系人");
    }

    @Override
    public void setPresenter(LinkmanContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
