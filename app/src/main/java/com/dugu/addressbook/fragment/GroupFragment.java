package com.dugu.addressbook.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.GroupDetailActivity;
import com.dugu.addressbook.adapter.recycleview.SimpleAdapter;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.GroupContract;
import com.dugu.addressbook.databinding.FragGroupBinding;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.item.GroupItemViewModel;

public class GroupFragment extends BaseFragment implements GroupContract.Ui {

    private GroupContract.Presenter presenter;
    private FragGroupBinding binding;
    private SimpleAdapter<GroupItemViewModel> simpleAdapter;

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
        simpleAdapter = new SimpleAdapter<>(R.layout.item_group, BR.GroupItemViewModel);
        binding.groupList.setAdapter(simpleAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.groupList.setLayoutManager(manager);
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

        simpleAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GroupDetailActivity.class);
                Group g = binding.getGroupViewModel().getGroupItemViewModels().get(position).getGroup();
                intent.putExtra(Constants.GROUPACTIVITY_GROUP_ID, g.getGroup_id());
                intent.putExtra(Constants.GROUPACTIVITY_GROUP_NAME, g.getGroup_name());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showResult() {
        binding.setGroupViewModel(presenter.getGroupViewModel());
        simpleAdapter.setmDatas(binding.getGroupViewModel().getGroupItemViewModels());
    }

    @Override
    public void setPresenter(GroupContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
