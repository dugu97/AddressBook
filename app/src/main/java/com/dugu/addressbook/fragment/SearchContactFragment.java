package com.dugu.addressbook.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.ContactDetailActivity;
import com.dugu.addressbook.adapter.SearchContactAdapter;
import com.dugu.addressbook.contract.SearchContactContract;
import com.dugu.addressbook.databinding.FragSearchContactBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.item.SearchContactItemViewModel;

import java.util.List;

public class SearchContactFragment extends BaseFragmentNoBar implements SearchContactContract.Ui {

    private SearchContactContract.Presenter presenter;
    private FragSearchContactBinding binding;
    private SearchContactAdapter adapter;


    public static SearchContactFragment newInstance(Bundle bundle) {
        SearchContactFragment fragment = new SearchContactFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_search_contact, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {

        binding.toolbar.setNavigationIcon(R.drawable.vector_drawable_left_arrow);
        binding.toolbar.inflateMenu(R.menu.navi_search_icon);

        adapter = new SearchContactAdapter();
        binding.contactList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.contactList.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        presenter.start();
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
                switch (item.getItemId()) {
                    case R.id.search:
                        makeToast("点击了");
                        final String key = binding.editQuery.getText().toString().replace(" ", "");
                        if (!AppUtil.isNullString(key)) {
                            showLoadingDialog("正在搜索中...");
                            presenter.searchContact(key);
                        }
                        break;
                }
                return true;
            }
        });

        adapter.setOnClickListener(new OnItemElementClickListener<SearchContactItemViewModel>() {
            @Override
            public void onClick(SearchContactItemViewModel obj, int position) {
                Intent intent = new Intent(getContext(), ContactDetailActivity.class);
                intent.putExtra(Constants.MAINACTIVITY_CONTACT_ID, obj.getContact().getContact_id());
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void showResult() {
        binding.setSearchContactViewModel(presenter.getSearchContactViewModel());
        List<SearchContactItemViewModel> viewModels = binding.getSearchContactViewModel().getItemViewModelList();
        if (viewModels.size() == 0)
            Log.d("123", "搜索到" + viewModels.size() + "个结果");
        adapter.setmItems(viewModels);
        closeLoadingDialog();
    }

    @Override
    public void setPresenter(SearchContactContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
