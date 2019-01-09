package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.ContactDetailActivity;
import com.dugu.addressbook.activity.NewOrEditContactActivity;
import com.dugu.addressbook.adapter.ContactSortedListAdapter;
import com.dugu.addressbook.adapter.ContactSortedListCallback;
import com.dugu.addressbook.adapter.recycleview.HidingScrollListener;
import com.dugu.addressbook.assembly.SideBar;
import com.dugu.addressbook.contract.ContactsContract;
import com.dugu.addressbook.databinding.FragMainLayoutBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.ContactsViewModel;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.List;

public class MainFragment extends BaseFragment implements ContactsContract.Ui {

    private ContactsContract.Presenter presenter;
    private ContactSortedListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private ContactsViewModel contactsViewModel;

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
        //启动presenter
        presenter.start();

        //配置sidebar
        binding.sidebar.setTextView(binding.dialog);

        //配置RecycleView
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.contactsRecycleView.setLayoutManager(linearLayoutManager);
        adapter = new ContactSortedListAdapter();
        ContactSortedListCallback sortedListCallback = new ContactSortedListCallback(adapter);
        SortedList<ContactItemViewModel> sortedList = new SortedList<>(ContactItemViewModel.class, sortedListCallback);
        adapter.setSortedList(sortedList);
        binding.contactsRecycleView.setAdapter(adapter);
    }


    @Override
    protected void initData() {
    }

    @Override
    protected void addListener() {
        binding.sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (adapter.getIndexMap().containsKey(s)) {
                    int indexPosition = adapter.getIndexMap().get(s);
                    MoveToPosition(linearLayoutManager, binding.contactsRecycleView, indexPosition);
                }
            }
        });

        adapter.setOnClickListener(new OnItemElementClickListener<ContactItemViewModel>() {
            @Override
            public void onClick(ContactItemViewModel obj, int position) {

                makeToast(position + "");

                if (obj.getContact_id() >= Constants.LIST_UTIL_INDEX) {
                    Intent intent = new Intent(getContext(), ContactDetailActivity.class);
                    intent.putExtra(Constants.MAINACTIVITY_CONTACT_ID, obj.getContact_id());
                    startActivity(intent);
                }
            }
        });

        adapter.setOnLongClickListener(new OnItemElementClickListener<ContactItemViewModel>() {
            @Override
            public void onClick(ContactItemViewModel obj, int position) {
                //即正式的联系人选项
                if (obj.getContact_id() > 0)
                    showAlertDialog(obj);
            }
        });

        binding.newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewOrEditContactActivity.class);
                intent.putExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, Constants.CONTACT_MODE_NEW_PHONE_CONTACT);
                startActivity(intent);
            }
        });

        binding.scanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.contactsRecycleView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                Resources resources = getActivity().getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                float density = dm.density;
                int width = dm.widthPixels;
                int height = dm.heightPixels;
                binding.SearchContainer.animate()
                        .translationY(-height)
                        .setDuration(800)
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();

            }

            @Override
            public void onShow() {
                binding.SearchContainer.animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2)
                        ).setDuration(800)
                        .start();
            }
        });

        binding.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etSearch.setText("");
                Log.d("123", AddressBookApplication.getDaoSession().getGroupDao().queryBuilder().list().size() + "");
                Log.d("123", AddressBookApplication.getDaoSession().getContactDao().queryBuilder().list().size() + "");
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                makeToast(s.toString());
                if ("".equals(s.toString()))
                    binding.deleteText.setVisibility(View.GONE);
                else
                    binding.deleteText.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 此方法是让recycleview滑动到指定位置，并且是让其到顶部
     *
     * @param manager
     * @param mRecyclerView
     * @param n
     */
    public void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }

    private void showAlertDialog(final ContactItemViewModel obj) {
        AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity()).setTitle(obj.getNameOrPhone())
                .setItems(Constants.CONTACT_LONG_CLICK_OPERATION_PROJECT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        makeToast("选择了第" + which + "个");
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
        binding.etSearch.setHint("在全部" + contactCount + "个联系人中搜索");

        adapter.setData(list);
    }

    @Override
    public void onResume() {
        super.onResume();

        //TODO 其它activity返回后要刷新联系人数据
    }


    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
