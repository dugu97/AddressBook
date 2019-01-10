package com.dugu.addressbook.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.ContactDetailMegSortedListAdapter;
import com.dugu.addressbook.adapter.ContactDetailMegSortedListCallback;
import com.dugu.addressbook.contract.ContactDetailContract;
import com.dugu.addressbook.databinding.FragContactDetailBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.ContactDetailItemViewModel;

public class ContactDetailFragment extends BaseFragmentNoBar implements ContactDetailContract.Ui {


    private FragContactDetailBinding binding;
    private ContactDetailContract.Presenter presenter;

    private ContactDetailMegSortedListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

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
        //启动presenter
        presenter.start();

        binding.toolbar.setNavigationIcon(R.drawable.vector_drawable_left_arrow);
        binding.toolbar.inflateMenu(R.menu.navi_qr_code_icon);
        binding.toolbarLayout.setTitleEnabled(false);

        Intent intent = getActivity().getIntent();
        Long contact_id = intent.getLongExtra(Constants.MAINACTIVITY_CONTACT_ID, -1);
        presenter.createContactDetailViewModel(contact_id);


        //配置RecycleView
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.contactDetailMegRecycleview.setLayoutManager(linearLayoutManager);
        adapter = new ContactDetailMegSortedListAdapter();
        ContactDetailMegSortedListCallback sortedListCallback = new ContactDetailMegSortedListCallback(adapter);
        SortedList<ContactDetailItemViewModel> sortedList = new SortedList<>(ContactDetailItemViewModel.class, sortedListCallback);
        adapter.setSortedList(sortedList);
        binding.contactDetailMegRecycleview.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.qr_code:
                        makeToast("点击了");
                        break;
                }
                return true;
            }
        });

        adapter.setOnClickListener(new OnItemElementClickListener<ContactDetailItemViewModel>() {
            @Override
            public void onClick(ContactDetailItemViewModel obj, int position) {
                makeToast("点击了" + position);
            }
        });

        adapter.setOnRightBtnClickListener(new OnItemElementClickListener<ContactDetailItemViewModel>() {
            @Override
            public void onClick(ContactDetailItemViewModel obj, int position) {
                if (obj.getSortKey() == Constants.SORTKEY_PHONE) {
                    makeToast("right");
//                    adapter.removeData(obj);
                    adapter.addData(new ContactDetailItemViewModel(Constants.SORTKEY_PHONE, "add" + position, "add"));
                }
            }
        });
    }

    @Override
    public void showResult() {
        //设置viewModel
        binding.setContactDetailViewModel(presenter.getContactDetailViewModel());

        adapter.setData(presenter.getAllMessageItems());
    }

    @Override
    public void setPresenter(ContactDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
