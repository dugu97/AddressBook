package com.dugu.addressbook.fragment;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.PhoneImportContactChooseAdapter;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.PhoneImportContactChooseContract;
import com.dugu.addressbook.databinding.FragPhoneImportContactChooseBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.util.MobileContactUtil;
import com.dugu.addressbook.viewmodel.PhoneImportContactChooseViewModel;
import com.dugu.addressbook.viewmodel.item.PhoneImportContactChooseItemViewModel;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PhoneImportContactChooseFragment extends BaseFragment implements PhoneImportContactChooseContract.Ui {

    private PhoneImportContactChooseContract.Presenter presenter;
    private FragPhoneImportContactChooseBinding binding;
    private List<PhoneImportContactChooseItemViewModel> viewModels;

    private PhoneImportContactChooseAdapter adapter;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            adapter.setmItems(viewModels);
            closeLoadingDialog();

            if (adapter.getItemCount() == 0) {
                binding.title.setVisibility(View.GONE);
                binding.cardView.setVisibility(View.GONE);
                binding.noContact.setVisibility(View.VISIBLE);
            } else {
                binding.title.setVisibility(View.VISIBLE);
                binding.cardView.setVisibility(View.VISIBLE);
                binding.noContact.setVisibility(View.GONE);
            }
            return true;
        }
    });

    public static PhoneImportContactChooseFragment newInstance(Bundle bundle) {
        PhoneImportContactChooseFragment fragment = new PhoneImportContactChooseFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_phone_import_contact_choose, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        adapter = new PhoneImportContactChooseAdapter();
        binding.contactList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.contactList.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        showLoadingDialog("正在读取手机联系人...");
        PhoneImportContactChooseFragmentPermissionsDispatcher.initContactDataWithPermissionCheck(PhoneImportContactChooseFragment.this);
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
                if (isMultiplicationClick())
                    return;

                List<PhoneImportContactChooseItemViewModel> list = adapter.getmItems();
                presenter.importContact(list);
                getActivity().finish();
            }
        });

        adapter.setOnClickListener(new OnItemElementClickListener<PhoneImportContactChooseItemViewModel>() {
            @Override
            public void onClick(PhoneImportContactChooseItemViewModel obj, int position) {

            }
        });

        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PhoneImportContactChooseItemViewModel> list = binding.getPhoneImportContactChooseViewModel().getViewModels();

                boolean isAllCheesed = true;

                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).isChecked())
                        isAllCheesed = false;
                }

                for (int i = 0; i < list.size(); i++) {
                    if (isAllCheesed)
                        list.get(i).setChecked(false);
                    else
                        list.get(i).setChecked(true);
                }

                adapter.setmItems(list);
            }
        });
    }

    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    public void initContactData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<ContactWithPhoneAndEmail> list = MobileContactUtil.getAllContactWithSim(getContext());
                    viewModels = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        viewModels.add(new PhoneImportContactChooseItemViewModel(list.get(i), false));
                    }
                    binding.setPhoneImportContactChooseViewModel(new PhoneImportContactChooseViewModel(viewModels));
                    handler.sendEmptyMessage(1);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhoneImportContactChooseFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void showResult() {

    }

    @Override
    public void setPresenter(PhoneImportContactChooseContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
