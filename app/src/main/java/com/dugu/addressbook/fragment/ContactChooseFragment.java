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
import com.dugu.addressbook.contract.ContactChooseContract;
import com.dugu.addressbook.databinding.FragContactChooseBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.ContactChooseItemViewModel;

import java.util.List;

public class ContactChooseFragment extends BaseFragment implements ContactChooseContract.Ui {

    private ContactChooseContract.Presenter presenter;
    private FragContactChooseBinding binding;
    private ContactChooseAdapter adapter;
    private int mode;

    public static ContactChooseFragment newInstance(Bundle bundle) {
        ContactChooseFragment fragment = new ContactChooseFragment();
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
                List<ContactChooseItemViewModel> list = adapter.getmItems();
                presenter.insertOrDeleteContactsToGroup(list, mode);
                getActivity().setResult(Constants.RESULT_CODE_OK);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter.getItemCount() == 0){
            binding.title.setVisibility(View.GONE);
            binding.noContact.setVisibility(View.VISIBLE);
        }else{
            binding.title.setVisibility(View.VISIBLE);
            binding.noContact.setVisibility(View.GONE);
        }
    }

    @Override
    public void showResult() {
        binding.setContactChooseViewModel(presenter.getContactChooseViewModel());
        List<ContactChooseItemViewModel> viewModels = binding.getContactChooseViewModel().getChooseItemViewModels();
        binding.getContactChooseViewModel().setChooseItemViewModels(viewModels);
        adapter.setmItems(binding.getContactChooseViewModel().getChooseItemViewModels());
    }

    @Override
    public void setPresenter(ContactChooseContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
