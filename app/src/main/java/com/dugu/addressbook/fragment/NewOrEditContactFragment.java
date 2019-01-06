package com.dugu.addressbook.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.ContactInputMegSortedListAdapter;
import com.dugu.addressbook.adapter.ContactInputMegSortedListCallback;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.NewOrEditContactContract;
import com.dugu.addressbook.databinding.FragEditAndNewContactBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

public class NewOrEditContactFragment extends BaseFragment implements NewOrEditContactContract.Ui {

    FragEditAndNewContactBinding binding;
    private NewOrEditContactContract.Presenter presenter;

    private int mode; //四种显示模式

    private ContactInputMegSortedListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public static NewOrEditContactFragment newInstance(Bundle bundle) {
        NewOrEditContactFragment fragment = new NewOrEditContactFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_edit_and_new_contact, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {

        Intent intent = getActivity().getIntent();
        mode = intent.getIntExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, -1);

        //-1 系统出错
        if (mode == -1)
            return;

        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT  || mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT){

            getABActionBar().setCenterTitleText("新建联系人");

            //配置RecycleView
            linearLayoutManager = new LinearLayoutManager(getContext());
            binding.contactInputList.setLayoutManager(linearLayoutManager);
            adapter = new ContactInputMegSortedListAdapter();
            ContactInputMegSortedListCallback sortedListCallback = new ContactInputMegSortedListCallback(adapter);
            SortedList<ContactInputItemViewModel> sortedList = new SortedList<>(ContactInputItemViewModel.class, sortedListCallback);
            adapter.setSortedList(sortedList);
            binding.contactInputList.setAdapter(adapter);

            if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT ){
                presenter.createViewModel(mode, null);
                adapter.setData(presenter.getNewOrEditContactViewModel().getInputList());
            }

            if (mode ==  Constants.CONTACT_MODE_EDIT_PHONE_CONTACT){
                long contact_id = intent.getLongExtra(Constants.ALLACTIVITY_CONTACT_ID, -1);
                if (contact_id != -1)
                    presenter.createViewModel(mode, contact_id);

                //TODO 初始列表
            }
        }
    }

    @Override
    protected void initData() {

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
                if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT  || mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT){
                    makeToast("确定");
                }
            }
        });

        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT  || mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT){
            adapter.setOnClickListener(new OnItemElementClickListener<ContactInputItemViewModel>() {
                @Override
                public void onClick(ContactInputItemViewModel obj, int position) {
                    makeToast(position + "");
                }
            });

            adapter.setOnTitleClickListener(new OnItemElementClickListener<ContactInputItemViewModel>() {
                @Override
                public void onClick(ContactInputItemViewModel obj, int position) {
                    if (obj.getSortKey() == Constants.SORTKEY_PHONE || obj.getSortKey() == Constants.SORTKEY_EMAIL){

                    }
                }
            });

            adapter.setOnAddMoreInputBtnClickListener(new OnItemElementClickListener<ContactInputItemViewModel>() {
                @Override
                public void onClick(ContactInputItemViewModel obj, int position) {
                    Log.d("123", adapter.getItemCount() + "个");
                    if (obj.getSortKey() == Constants.SORTKEY_PHONE ){
                        adapter.addData(new ContactInputItemViewModel(Constants.SORTKEY_PHONE, adapter.getItemCount() + 1, "手机", null));
                    }else if (obj.getSortKey() == Constants.SORTKEY_EMAIL){
                        adapter.addData(new ContactInputItemViewModel(Constants.SORTKEY_EMAIL, adapter.getItemCount() + 1, "私人", null));
                    }
                }
            });
        }
    }

    @Override
    public void setPresenter(NewOrEditContactContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showResult() {

    }
}
