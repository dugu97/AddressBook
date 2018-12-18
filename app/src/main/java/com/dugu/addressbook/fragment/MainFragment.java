package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.adapter.ContactSortedListAdapter;
import com.dugu.addressbook.adapter.ContactSortedListCallback;
import com.dugu.addressbook.assembly.SideBar;
import com.dugu.addressbook.contract.LinkmanContract;
import com.dugu.addressbook.databinding.FragMainLayoutBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.ContactItemViewModel;

public class MainFragment extends BaseFragment implements LinkmanContract.Ui {

    private LinkmanContract.Presenter presenter;
    private ContactItemViewModel linkManViewModel;
    private ContactSortedListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

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
        //配置sidebar
        binding.sidebar.setTextView(binding.dialog);

        Log.d("123", getContext().toString());

        //配置RecycleView
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.contactsRecycleView.setLayoutManager(linearLayoutManager);
        adapter = new ContactSortedListAdapter();
        ContactSortedListCallback sortedListCallback = new ContactSortedListCallback(adapter);
        SortedList<ContactItemViewModel> sortedList = new SortedList<>(ContactItemViewModel.class, sortedListCallback);
        adapter.setSortedList(sortedList);
        binding.contactsRecycleView.setAdapter(adapter);

        binding.contactsRecycleView.setHasFixedSize(true);
        binding.contactsRecycleView.setNestedScrollingEnabled(false);
    }


    @Override
    protected void initData() {
//        presenter.start();

        //配置联系人数据
        adapter.setData(presenter.getAllContact());

        //配置弹出框
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

        binding.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etSearch.setText("");
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

    private void showAlertDialog(ContactItemViewModel obj){
        AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity()).setTitle(obj.getNameOrPhone())
                .setItems(Constants.MAINFRAGOPERATION_PROJECT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        makeToast("选择了第"+which+"个");
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    public void showResult() {
        Log.d("123", "加载了" + contactCount + "个联系人");
    }

    @Override
    public void setPresenter(LinkmanContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
