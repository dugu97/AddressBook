package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.BusinessCardActivity;
import com.dugu.addressbook.activity.ContactDetailActivity;
import com.dugu.addressbook.activity.GroupActivity;
import com.dugu.addressbook.activity.ImportAndExportActivity;
import com.dugu.addressbook.activity.NewOrEditContactActivity;
import com.dugu.addressbook.activity.ScanActivity;
import com.dugu.addressbook.activity.SearchContactActivity;
import com.dugu.addressbook.adapter.ContactsAdapter;
import com.dugu.addressbook.adapter.CustomItemDecoration;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.assembly.SideBar;
import com.dugu.addressbook.contract.ContactsContract;
import com.dugu.addressbook.databinding.FragMainLayoutBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.util.CommonUtil;
import com.dugu.addressbook.util.VCardUtil;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment implements ContactsContract.Ui {

    private ContactsContract.Presenter presenter;
    private ContactsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private CustomItemDecoration decoration;

    //用于临时生成的分享文件
    private File file;
    private Boolean isCreateTempFile = false;

    private FragMainLayoutBinding binding;

    public static MainFragment newInstance(Bundle bundle) {
        MainFragment fragment = new MainFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_main_layout, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {

    }


    @Override
    protected void initData() {
        adapter = new ContactsAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.contactsRecycleView.setLayoutManager(linearLayoutManager);
        binding.contactsRecycleView.addItemDecoration(decoration = new CustomItemDecoration(getActivity()));
        binding.contactsRecycleView.setAdapter(adapter);

        //启动presenter
        presenter.start();
    }

    @Override
    protected void addListener() {

        getABActionBar().addDuguToolBarCallBack(new ABToolBar.ABToolBarCallBack() {
            @Override
            public void onFallbackClickCallBack() {

            }

            @Override
            public void onRightButtonClickCallBack() {
                Intent intent = new Intent(getContext(), SearchContactActivity.class);
                startActivity(intent);
            }
        });

        binding.sideBar.setIndexChangeListener(new SideBar.indexChangeListener() {
            @Override
            public void indexChanged(String tag) {
                if (TextUtils.isEmpty(tag) || binding.getContactsViewModel().getContacts().size() <= 0)
                    return;
                for (int i = 0; i < binding.getContactsViewModel().getContacts().size(); i++) {
                    if (tag.equals(binding.getContactsViewModel().getContacts().get(i).getFirstPingYin())) {
                        linearLayoutManager.scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });

        adapter.setOnClickListener(new OnItemElementClickListener<ContactItemViewModel>() {
            @Override
            public void onClick(ContactItemViewModel obj, int position) {

                makeToast(position + "DDD" + obj.getContact().getContact_id());

                if (obj.getContact().getContact_id() >= Constants.LIST_UTIL_INDEX) {
                    Intent intent = new Intent(getContext(), ContactDetailActivity.class);
                    intent.putExtra(Constants.MAINACTIVITY_CONTACT_ID, obj.getContact().getContact_id());
                    startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_CONTACT);
                } else {
                    if (obj.getContact().getContact_id() == Constants.UTIL_GROUP_INDEX) {
                        Intent intent = new Intent(getContext(), GroupActivity.class);
                        startActivity(intent);
                    } else if (obj.getContact().getContact_id() == Constants.UTIL_BUSINESS_CARD_INDEX) {
                        Intent intent = new Intent(getContext(), BusinessCardActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        adapter.setOnLongClickListener(new OnItemElementClickListener<ContactItemViewModel>() {
            @Override
            public void onClick(ContactItemViewModel obj, int position) {
                //即正式的联系人选项
                if (obj.getContact().getContact_id() > 0) {
                    List<Group> groupList = obj.getContact().getGroupList();
                    for (Group p : groupList) {
                        Log.d("123", "group" + p.getGroup_id());
                        if (p.getGroup_id() == Constants.GROUP_BLACK) {
                            showAlertDialog(obj, position, true);
                            return;
                        }
                    }
                    showAlertDialog(obj, position, false);
                }

            }
        });

        binding.newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewOrEditContactActivity.class);
                intent.putExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, Constants.CONTACT_MODE_NEW_PHONE_CONTACT);
                startActivityForResult(intent, Constants.REQUEST_CODE_NEW_CONTACT);
            }
        });

        binding.scanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        binding.importAndExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImportAndExportActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();

        if (isCreateTempFile && file != null && file.exists()) {
            file.delete();
            isCreateTempFile = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_CODE_OK) {
            if (requestCode == Constants.REQUEST_CODE_NEW_CONTACT || requestCode == Constants.REQUEST_CODE_EDIT_CONTACT) {
                presenter.start();
            }
        }
    }

    private void showAlertDialog(final ContactItemViewModel obj, final int position, final boolean isInBlack) {
        String[] temp;

        if (!isInBlack)
            temp = Constants.CONTACT_LONG_CLICK_OPERATION_PROJECT;
        else
            temp = Constants.CONTACT_LONG_CLICK_OPERATION_PROJECT_2;

        AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity()).setTitle(obj.getNameOrPhone())
                .setItems(temp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == Constants.CONTACT_LONG_CLICK_OPERATION_DELETE) {
                            adapter.remove(position);
                            binding.getContactsViewModel().getContacts().remove(position);
                            presenter.deleteContact(obj.getContact().getContact_id());
                        } else if (which == Constants.CONTACT_LONG_CLICK_OPERATION_EDIT) {
                            Intent intent = new Intent(getContext(), ContactDetailActivity.class);
                            intent.putExtra(Constants.MAINACTIVITY_CONTACT_ID, obj.getContact().getContact_id());
                            startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_CONTACT);
                        } else if (which == Constants.CONTACT_LONG_CLICK_OPERATION_ADD_OR_DELETE_BLACK) {
                            if (!isInBlack)
                                presenter.addContactInBlack(obj.getContact().getContact_id());
                            else
                                presenter.deleteContactInBlack(obj.getContact().getContact_id());

                            //涉及数据变化,要进行刷新
                            presenter.start();
                        }else if (which == Constants.CONTACT_LONG_CLICK_OPERATION_SHARE_CONTACT){
                            showLoadingDialog("正在生成VCard文件...");
                            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppUtil.formatFileNameWithTime(System.currentTimeMillis()));
                            isCreateTempFile = true;
                            List<Contact> contacts = new ArrayList<>();
                            contacts.add(obj.getContact());
                            try {
                                VCardUtil.createVCard(contacts, file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            closeLoadingDialog();
                            AppUtil.shareFile(getContext(),file);
                        }

                    }
                }).create();
        alertDialog.show();
    }

    @Override
    public void showResult() {
        //绑定viewModel对象
        binding.setContactsViewModel(presenter.getContactsViewModel());

        //配置联系人数据,并刷新
        List<ContactItemViewModel> list = binding.getContactsViewModel().getContacts();

        //对数据源进行排序
        CommonUtil.sortContactItemViewModelData(list);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(list);
//        binding.sideBar.setIndexStr("#ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        decoration.setDatas(list, tagsStr);
        adapter.addAll(list);

        Log.d("123", list.size() + "ggg");
    }

    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
