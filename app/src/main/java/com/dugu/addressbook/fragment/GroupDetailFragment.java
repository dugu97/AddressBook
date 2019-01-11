package com.dugu.addressbook.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.ContactDetailActivity;
import com.dugu.addressbook.adapter.GroupDetailAdapter;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.GroupDetailContract;
import com.dugu.addressbook.databinding.FragGroupDetailBinding;

public class GroupDetailFragment extends BaseFragment implements GroupDetailContract.Ui {

    private GroupDetailContract.Presenter presenter;
    private FragGroupDetailBinding binding;
    private GroupDetailAdapter adapter;

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
        Intent intent = getActivity().getIntent();
        Long group_id = intent.getLongExtra(Constants.GROUPACTIVITY_GROUP_ID, -1);
        String group_name = intent.getStringExtra(Constants.GROUPACTIVITY_GROUP_NAME);
        getABActionBar().setTitleText(group_name);

        presenter.setGroupId(group_id);

        adapter = new GroupDetailAdapter();
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
        getABActionBar().addDuguToolBarCallBack(new ABToolBar.ABToolBarCallBack() {
            @Override
            public void onFallbackClickCallBack() {
                getActivity().finish();
            }

            @Override
            public void onRightButtonClickCallBack() {

            }
        });

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long contact_id = binding.getGroupDetailViewModel().getViewModels().get(position).getContact().getContact_id();
                Intent intent = new Intent(getContext(), ContactDetailActivity.class);
                intent.putExtra(Constants.MAINACTIVITY_CONTACT_ID, contact_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showResult() {
        binding.setGroupDetailViewModel(presenter.getGroupDetailViewModel());
        adapter.setmItems(binding.getGroupDetailViewModel().getViewModels());
    }

    @Override
    public void setPresenter(GroupDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
