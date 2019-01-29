package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.ContactsAdapter;
import com.dugu.addressbook.contract.BusinessCardContract;
import com.dugu.addressbook.databinding.FragBusinessCardBinding;

public class BusinessCardFragment extends BaseFragment implements BusinessCardContract.Ui{

    private BusinessCardContract.Presenter presenter;
    private FragBusinessCardBinding binding;
    private ContactsAdapter adapter;

    public static BusinessCardFragment newInstance(Bundle bundle) {
        BusinessCardFragment fragment = new BusinessCardFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_business_card, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        adapter = new ContactsAdapter();
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

    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.start();

        if (adapter.getItemCount() == 0){
            binding.title.setVisibility(View.GONE);
            binding.noContact.setVisibility(View.VISIBLE);
            binding.cardView.setVisibility(View.GONE);
        }else{
            binding.title.setVisibility(View.VISIBLE);
            binding.noContact.setVisibility(View.GONE);
            binding.cardView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showResult() {
        binding.setBusinessCardViewModel(presenter.getBusinessCardViewModel());
        adapter.setmItems(binding.getBusinessCardViewModel().getViewModels());
    }

    @Override
    public void setPresenter(BusinessCardContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
