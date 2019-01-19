package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.dugu.addressbook.adapter.ContactsAdapter;
import com.dugu.addressbook.adapter.CustomItemDecoration;
import com.dugu.addressbook.assembly.SideBar;
import com.dugu.addressbook.contract.ContactsContract;
import com.dugu.addressbook.databinding.FragMainLayoutBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.util.CommonUtil;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.List;

public class MainFragment extends BaseFragment implements ContactsContract.Ui {

    private ContactsContract.Presenter presenter;
    private ContactsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private CustomItemDecoration decoration;


    private int contactCount;


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
        //配置sidebar
//        binding.sidebar.setTextView(binding.dialog);

        //配置RecycleView
//        adapter = new ContactSortedListAdapter();
//        ContactSortedListCallback sortedListCallback = new ContactSortedListCallback(adapter);
//        SortedList<ContactItemViewModel> sortedList = new SortedList<>(ContactItemViewModel.class, sortedListCallback);
//        adapter.setSortedList(sortedList);
//        binding.contactsRecycleView.setAdapter(adapter);
//        linearLayoutManager = new LinearLayoutManager(getContext());
//        binding.contactsRecycleView.setLayoutManager(linearLayoutManager);

        adapter = new ContactsAdapter();
        //侧边导航栏
//        side_bar = (SideBar) findViewById(R.id.side_bar);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.contactsRecycleView.setLayoutManager(linearLayoutManager);
        binding.contactsRecycleView.addItemDecoration(decoration = new CustomItemDecoration(getActivity()));
//        initDatas();
        binding.contactsRecycleView.setAdapter(adapter);

        //启动presenter
        presenter.start();
    }

    @Override
    protected void addListener() {
//        binding.sidebar.setOnTouchingLetterChangedListener(new SidBar.OnTouchingLetterChangedListener() {
//            @Override
//            public void onTouchingLetterChanged(String s) {
//                if (adapter.getIndexMap().containsKey(s)) {
//                    int indexPosition = adapter.getIndexMap().get(s);
//                    MoveToPosition(linearLayoutManager, binding.contactsRecycleView, indexPosition);
//                }
//            }
//        });

        binding.sideBar.setIndexChangeListener(new SideBar.indexChangeListener() {
            @Override
            public void indexChanged(String tag) {
                if (TextUtils.isEmpty(tag) || binding.getContactsViewModel().getContacts().size() <= 0) return;
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
                }else {
                    if (obj.getContact().getContact_id() == Constants.UTIL_GROUP_INDEX){
                        Intent intent = new Intent(getContext(), GroupActivity.class);
                        startActivity(intent);
                    }else if (obj.getContact().getContact_id() == Constants.UTIL_BUSINESS_CARD_INDEX){
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
                if (obj.getContact().getContact_id() > 0)
                    showAlertDialog(obj, position);
            }
        });

        binding.newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewOrEditContactActivity.class);
                intent.putExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, Constants.CONTACT_MODE_NEW_PHONE_CONTACT);
                startActivityForResult(intent,Constants.REQUEST_CODE_NEW_CONTACT);
            }
        });

        binding.scanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.moreOperation.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_CODE_OK){
            if (requestCode == Constants.REQUEST_CODE_NEW_CONTACT || requestCode == Constants.REQUEST_CODE_EDIT_CONTACT){
                presenter.start();
            }
        }
    }

    private void showAlertDialog(final ContactItemViewModel obj, final int position) {
        AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity()).setTitle(obj.getNameOrPhone())
                .setItems(Constants.CONTACT_LONG_CLICK_OPERATION_PROJECT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == Constants.CONTACT_LONG_CLICK_OPERATION_DELETE) {
                            adapter.remove(position);
                            binding.getContactsViewModel().getContacts().remove(position);
                            presenter.deleteContact(obj.getContact().getContact_id());
//                            adapter.removeData(position);
                        }else if (which == Constants.CONTACT_LONG_CLICK_OPERATION_ADD_BLACK){
                            presenter.addContactInBlack(obj.getContact().getContact_id());
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

        //配置联系人总数
        contactCount = binding.getContactsViewModel().getContactsSizeWithoutOtherUtilItem();

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
