package com.dugu.addressbook.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.GroupChooseAdapter;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.GroupChooseContract;
import com.dugu.addressbook.databinding.FragGroupChooseBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.GroupChooseItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupChooseFragment extends BaseFragment implements GroupChooseContract.Ui {

    private FragGroupChooseBinding binding;
    private GroupChooseContract.Presenter presenter;

    private GroupChooseAdapter adapter;

    public static GroupChooseFragment newInstance(Bundle bundle) {
        GroupChooseFragment fragment = new GroupChooseFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_group_choose, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        adapter = new GroupChooseAdapter();
        binding.groupList.setAdapter(adapter);
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
        adapter.setOnClickListener(new OnItemElementClickListener<GroupChooseItemViewModel>() {
            @Override
            public void onClick(GroupChooseItemViewModel obj, int position) {
                Log.d("123", obj.isChecked() + "");
            }
        });

        getABActionBar().addDuguToolBarCallBack(new ABToolBar.ABToolBarCallBack() {
            @Override
            public void onFallbackClickCallBack() {
                getActivity().finish();
            }

            @Override
            public void onRightButtonClickCallBack() {
                if (isMultiplicationClick())
                    return;

                ArrayList<String> groupChooseList = new ArrayList<>();
                List<GroupChooseItemViewModel> list = adapter.getmItems();
                for (GroupChooseItemViewModel model : list) {
                    if (model.isChecked()) {
                        //id + 名字 注意次序
                        groupChooseList.add(model.getGroup().getGroup_id() + " " + model.getGroup().getGroup_name());
                    }
                }
                Intent intent = new Intent();
                intent.putStringArrayListExtra(Constants.ALLACTIVITY_GROUPS_CHOOSE, groupChooseList);
                getActivity().setResult(Constants.RESULT_CODE_OK, intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void showResult() {
        binding.setGroupChooseViewModel(presenter.getGroupChooseViewModel());

        Intent intent = getActivity().getIntent();
        ArrayList<String> arrayList = intent.getStringArrayListExtra(Constants.ALLACTIVITY_GROUPS_CHOOSE);
        List<GroupChooseItemViewModel> viewModels = binding.getGroupChooseViewModel().getGroupListViewModel();
        for (int i = 0; i < viewModels.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (viewModels.get(i).getGroup().getGroup_id() == Long.parseLong(arrayList.get(j)))
                    viewModels.get(i).setChecked(true);
            }
        }
        binding.getGroupChooseViewModel().setGroupListViewModel(viewModels);
        adapter.setmItems(binding.getGroupChooseViewModel().getGroupListViewModel());
    }


    @Override
    public void setPresenter(GroupChooseContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
