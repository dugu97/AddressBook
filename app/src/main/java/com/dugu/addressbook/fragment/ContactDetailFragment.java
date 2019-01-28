package com.dugu.addressbook.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.NewOrEditContactActivity;
import com.dugu.addressbook.adapter.ContactDetailMegAdapter;
import com.dugu.addressbook.contract.ContactDetailContract;
import com.dugu.addressbook.databinding.FragContactDetailBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.util.CommonUtil;
import com.dugu.addressbook.util.DataCheckUtil;
import com.dugu.addressbook.viewmodel.item.ContactDetailItemViewModel;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ContactDetailFragment extends BaseFragmentNoBar implements ContactDetailContract.Ui {


    private FragContactDetailBinding binding;
    private ContactDetailContract.Presenter presenter;

    private Long contact_id;

    private ContactDetailMegAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public static ContactDetailFragment newInstance(Bundle bundle) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_contact_detail, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {

        binding.toolbar.setNavigationIcon(R.drawable.vector_drawable_left_arrow);
        binding.toolbar.inflateMenu(R.menu.navi_qr_code_icon);
        binding.toolbarLayout.setTitleEnabled(false);

        Intent intent = getActivity().getIntent();
        contact_id = intent.getLongExtra(Constants.MAINACTIVITY_CONTACT_ID, -1);
        if (contact_id != -1)
            presenter.createContactDetailViewModel(contact_id);


        //配置RecycleView
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        binding.contactDetailMegRecycleview.setLayoutManager(linearLayoutManager);
        adapter = new ContactDetailMegAdapter();
//        ContactDetailMegSortedListCallback sortedListCallback = new ContactDetailMegSortedListCallback(adapter);
//        SortedList<ContactDetailItemViewModel> sortedList = new SortedList<>(ContactDetailItemViewModel.class, sortedListCallback);
//        adapter.setSortedList(sortedList);
        binding.contactDetailMegRecycleview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        //启动presenter
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
                    case R.id.qr_code:
                        makeToast("点击了");
                        break;
                }
                return true;
            }
        });

        adapter.setOnClickListener(new OnItemElementClickListener<ContactDetailItemViewModel>() {
            @Override
            public void onClick(ContactDetailItemViewModel obj, int position) {
                if (obj.getSortKey() == Constants.SORTKEY_PHONE) {
                    String phone = obj.getContent().replace(" ", "");
                    if (DataCheckUtil.isMobile(phone)) {
                        ContactDetailFragmentPermissionsDispatcher.callPhoneWithPermissionCheck(ContactDetailFragment.this, phone, getActivity());
                    } else {
                        makeToast("手机号格式错误");
                    }
                } else if (obj.getSortKey() == Constants.SORTKEY_EMAIL) {
                    String email = obj.getContent().replace(" ", "");
                    if (DataCheckUtil.isEmail(email))
                        AppUtil.copyText(getActivity(), email, "已复制到剪切板");
                    else{
                        makeToast("邮件格式错误");
                    }
                } else if (obj.getSortKey() == Constants.SORTKEY_ADDRESS) {
                    String addresss = obj.getContent().replace(" ", "");
                    AppUtil.copyText(getActivity(), addresss, "已复制到剪切板");
                }
            }
        });

        adapter.setOnLongClickListener(new OnItemElementClickListener<ContactDetailItemViewModel>() {
            @Override
            public void onClick(ContactDetailItemViewModel obj, int position) {

            }
        });

        adapter.setOnRightBtnClickListener(new OnItemElementClickListener<ContactDetailItemViewModel>() {
            @Override
            public void onClick(ContactDetailItemViewModel obj, int position) {
                if (obj.getSortKey() == Constants.SORTKEY_PHONE) {
                    String phone = obj.getContent().replace(" ", "");
                    if (DataCheckUtil.isMobile(phone)) {
                        sendSms(phone);
                    } else {
                        makeToast("手机号格式错误");
                    }
                }
            }
        });

        binding.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewOrEditContactActivity.class);
                intent.putExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, Constants.CONTACT_MODE_EDIT_PHONE_CONTACT);
                intent.putExtra(Constants.ALLACTIVITY_CONTACT_ID, binding.getContactDetailViewModel().getContact_id());
                startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_CONTACT);
            }
        });

        binding.moreOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    public void callPhone(String phone, Context context) {
        AppUtil.callPhone(phone, context);
    }

    public void sendSms(String phone) {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ContactDetailFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_CODE_OK)
            if (requestCode == Constants.REQUEST_CODE_EDIT_CONTACT) {
                //重启Activity刷新数据 并通知MainActivity刷新
                Intent intent = getActivity().getIntent();
                getActivity().startActivity(intent);
                getActivity().setResult(Constants.RESULT_CODE_OK);
                getActivity().finish();
            }

    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity())
                .setItems(Constants.CONTACT_DETAIL_MORE_OPERATION_PROJECT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == Constants.CONTACT_DETAIL_MORE_OPERATION_DELETE) {
                            //删除联系人
                            presenter.deleteContact(binding.getContactDetailViewModel().getContact_id());
                            getActivity().finish();
                        } else if (which == Constants.CONTACT_DETAIL_MORE_OPERATION_SHARE) {
                            //分享联系

                        }
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    public void showResult() {
        //设置viewModel
        binding.setContactDetailViewModel(presenter.getContactDetailViewModel());
        List<ContactDetailItemViewModel> list = presenter.getAllMessageItems();
        CommonUtil.sortContactDetailItemViewModelData(list);
        adapter.addAll(list);
    }

    @Override
    public void setPresenter(ContactDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
