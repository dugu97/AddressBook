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
import com.dugu.addressbook.adapter.ContactChooseAdapter;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.GroupContactChooseContract;
import com.dugu.addressbook.databinding.FragContactChooseBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.ContactChooseItemViewModel;

import java.util.List;

public class GroupContactChooseFragment extends BaseFragment implements GroupContactChooseContract.Ui {

    private GroupContactChooseContract.Presenter presenter;
    private FragContactChooseBinding binding;
    private ContactChooseAdapter adapter;
    private int mode;

    public static GroupContactChooseFragment newInstance(Bundle bundle) {
        GroupContactChooseFragment fragment = new GroupContactChooseFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_contact_choose, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        adapter = new ContactChooseAdapter();
        binding.contactList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.contactList.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        Intent intent = getActivity().getIntent();
        Long group_id = intent.getLongExtra(Constants.ALLACTIVITY_GROUP_ID, -1);
        mode = intent.getIntExtra(Constants.MODE_CONTACTCHOOSE_ACTIVITY, -1);
        if (group_id != -1 && mode != -1)
            presenter.createContactChooseViewModel(group_id, mode);
    }

    @Override
    protected void addListener() {
        adapter.setOnClickListener(new OnItemElementClickListener<ContactChooseItemViewModel>() {
            @Override
            public void onClick(ContactChooseItemViewModel obj, int position) {
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

                List<ContactChooseItemViewModel> list = adapter.getmItems();
                presenter.insertOrDeleteContactsToGroup(list, mode);
                getActivity().setResult(Constants.RESULT_CODE_OK);
                getActivity().finish();
            }
        });

        binding.chooseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ContactChooseItemViewModel> list = binding.getGroupContactChooseViewModel().getChooseItemViewModels();

                boolean isAllChoosed = true;

                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).isChecked())
                        isAllChoosed = false;
                }

                for (int i = 0; i < list.size(); i++) {
                    if (isAllChoosed)
                        list.get(i).setChecked(false);
                    else
                        list.get(i).setChecked(true);
                }

                adapter.setmItems(list);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter.getItemCount() == 0) {
            binding.title.setVisibility(View.GONE);
            binding.cardView.setVisibility(View.GONE);
            binding.noContact.setVisibility(View.VISIBLE);
        } else {
            binding.title.setVisibility(View.VISIBLE);
            binding.cardView.setVisibility(View.VISIBLE);
            binding.noContact.setVisibility(View.GONE);
        }
    }

    @Override
    public void showResult() {
        binding.setGroupContactChooseViewModel(presenter.getContactChooseViewModel());
        List<ContactChooseItemViewModel> viewModels = binding.getGroupContactChooseViewModel().getChooseItemViewModels();
        binding.getGroupContactChooseViewModel().setChooseItemViewModels(viewModels);
        adapter.setmItems(binding.getGroupContactChooseViewModel().getChooseItemViewModels());
    }

    @Override
    public void setPresenter(GroupContactChooseContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
