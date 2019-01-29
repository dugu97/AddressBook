package com.dugu.addressbook.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.ContactChooseAdapter;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.ShareContactChooseContract;
import com.dugu.addressbook.databinding.FragShareContactChooseBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.util.VCardUtil;
import com.dugu.addressbook.viewmodel.item.ContactChooseItemViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShareContactChooseFragment extends BaseFragment implements ShareContactChooseContract.Ui {

    private ShareContactChooseContract.Presenter presenter;
    private FragShareContactChooseBinding binding;

    private ContactChooseAdapter adapter;

    private File file;
    private boolean isCreateTempFile = false;

    public static ShareContactChooseFragment newInstance(Bundle bundle) {
        ShareContactChooseFragment fragment = new ShareContactChooseFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_share_contact_choose, container, false);
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
        presenter.start();
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

                if (adapter.getItemCount() == 0) {
                    getActivity().finish();
                    return;
                }

                List<ContactChooseItemViewModel> list = adapter.getmItems();
                file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppUtil.formatFileNameWithTime(System.currentTimeMillis()));
                isCreateTempFile = true;
                List<Contact> contacts = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked())
                        contacts.add(list.get(i).getContact());
                }

                if (contacts.size() > 0) {
                    showLoadingDialog("正在生成VCard文件...");
                    try {
                        VCardUtil.createVCard(contacts, file);
                        AppUtil.shareFile(getContext(), file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        binding.chooseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ContactChooseItemViewModel> list = binding.getShareContactChooseViewModel().getViewModels();

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

        //分享结束后再终结Activity
        if (isCreateTempFile){
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //删除临时文件
        if (isCreateTempFile && file != null && file.exists()) {
            file.delete();
            isCreateTempFile = false;
        }
    }

    @Override
    public void showResult() {
        binding.setShareContactChooseViewModel(presenter.getShareContactChooseViewModel());
        List<ContactChooseItemViewModel> viewModels = binding.getShareContactChooseViewModel().getViewModels();
        adapter.setmItems(viewModels);
    }

    @Override
    public void setPresenter(ShareContactChooseContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
