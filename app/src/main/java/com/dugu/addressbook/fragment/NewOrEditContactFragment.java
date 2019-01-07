package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.GroupChooseActivity;
import com.dugu.addressbook.adapter.ContactInputMegSortedListAdapter;
import com.dugu.addressbook.adapter.ContactInputMegSortedListCallback;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.NewOrEditContactContract;
import com.dugu.addressbook.databinding.FragEditAndNewContactBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewOrEditContactFragment extends BaseFragment implements NewOrEditContactContract.Ui {

    private FragEditAndNewContactBinding binding;
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
        //启动presenter
        presenter.start();


        Intent intent = getActivity().getIntent();
        mode = intent.getIntExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, -1);

        //-1 系统出错
        if (mode == -1)
            return;

        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT || mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT) {

            getABActionBar().setCenterTitleText("新建联系人");

            //配置RecycleView
            linearLayoutManager = new LinearLayoutManager(getContext());
            binding.contactInputList.setLayoutManager(linearLayoutManager);
            adapter = new ContactInputMegSortedListAdapter();
            ContactInputMegSortedListCallback sortedListCallback = new ContactInputMegSortedListCallback(adapter);
            SortedList<ContactInputItemViewModel> sortedList = new SortedList<>(ContactInputItemViewModel.class, sortedListCallback);
            adapter.setSortedList(sortedList);
            binding.contactInputList.setAdapter(adapter);

            if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT) {
                presenter.createViewModel(mode, null);
                adapter.setData(presenter.getNewOrEditContactViewModel().getInputList());
                binding.setNewOrEditContactViewModel(presenter.getNewOrEditContactViewModel());
            }

            if (mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT) {
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
                if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT || mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT) {
                    makeToast("确定");
                }
            }
        });

        binding.chooseContactMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog
                        .Builder(getActivity())
                        .setItems(Constants.MODE_CHOOSE_PROJECT, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                makeToast(Constants.MODE_CHOOSE_PROJECT[which]);
                            }
                        }).create();
                alertDialog.show();
            }
        });

        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT || mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT) {
            adapter.setOnTitleClickListener(new OnItemElementClickListener<ContactInputItemViewModel>() {
                @Override
                public void onClick(final ContactInputItemViewModel obj, int position) {
                    if (obj.getSortKey() == Constants.SORTKEY_PHONE || obj.getSortKey() == Constants.SORTKEY_EMAIL) {
                        makeToast("title");
                        AlertDialog alertDialog = new AlertDialog
                                .Builder(getActivity())
                                .setItems(Constants.PHONE_LABEL_PROJECT, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String temp = Constants.PHONE_LABEL_PROJECT[which];
                                        ContactInputItemViewModel viewModel = new ContactInputItemViewModel(obj.getSortKey(), obj.getSerialNumber(), temp, obj.getContent());
                                        adapter.addData(viewModel);
                                        binding.getNewOrEditContactViewModel().getInputList().remove(obj);
                                        binding.getNewOrEditContactViewModel().getInputList().add(viewModel);
                                    }
                                }).create();
                        alertDialog.show();
                    }
                }
            });

            adapter.setOnAddMoreInputBtnClickListener(new OnItemElementClickListener<ContactInputItemViewModel>() {
                @Override
                public void onClick(ContactInputItemViewModel obj, int position) {
                    Log.d("123", adapter.getItemCount() + "个");
                    ContactInputItemViewModel viewModel;
                    if (obj.getSortKey() == Constants.SORTKEY_PHONE) {
                        viewModel = new ContactInputItemViewModel(Constants.SORTKEY_PHONE, adapter.getItemCount() + 1, "手机", null);
                        adapter.addData(viewModel);
                        binding.getNewOrEditContactViewModel().getInputList().add(viewModel);
                    } else if (obj.getSortKey() == Constants.SORTKEY_EMAIL) {
                        viewModel = new ContactInputItemViewModel(Constants.SORTKEY_EMAIL, adapter.getItemCount() + 1, "私人", null);
                        adapter.addData(viewModel);
                        binding.getNewOrEditContactViewModel().getInputList().add(viewModel);
                    }
                }
            });

            binding.addBirthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            binding.birthdayContent.setText(AppUtil.formatTimeInMillis(date.getTime()));
                        }
                    }).setCancelColor(0xff00ACC2).setCancelText("取消").setSubmitColor(0xff00ACC2).setSubmitText("确定").build();
                    pvTime.show();
                }
            });

//            binding.addRingLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String myUriStr = null;
//                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
//                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
//                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置来电铃声");
//                    if (myUriStr != null) {
//                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(myUriStr));//将已经勾选过的铃声传递给系统铃声界面进行显示
//                    }
//                    startActivityForResult(intent, 0);
//
//                }
//            });

            binding.addGroupLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GroupChooseActivity.class);
                    //适配已选择的group
                    ArrayList<String> arrayList = new ArrayList<>();
                    Log.d("123", binding.getNewOrEditContactViewModel().getGroupList().size() + " all");
                    for (Group g : binding.getNewOrEditContactViewModel().getGroupList()) {
                        if (g.getGroup_id() != Constants.GROUP_PHONE
                                && g.getGroup_id() != Constants.GROUP_SIM
                                && g.getGroup_id() != Constants.GROUP_CARD) {
                            arrayList.add(g.getGroup_id() + "");
                        }
                    }
                    Log.d("123", arrayList.size() + " send");

                    intent.putStringArrayListExtra(Constants.NEWOREDITCONTACTACTIVITY_GROUPS, arrayList);
                    startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE_GROUP);
                }
            });

            binding.addOtherMeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (ContactInputItemViewModel viewModel : binding.getNewOrEditContactViewModel().getInputList()) {
                        Log.d("123", viewModel.toString());
                    }
//                    makeToast(binding.getNewOrEditContactViewModel().getJob())

                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_CODE_OK)
            if (requestCode == Constants.REQUEST_CODE_CHOOSE_GROUP) {
                ArrayList<String> group = data.getStringArrayListExtra(Constants.NEWOREDITCONTACTACTIVITY_GROUPS);
                if (group == null)
                    return;
                List<Group> groupList = binding.getNewOrEditContactViewModel().getGroupList();
                List<Group> result = new ArrayList<>();
                for (int i = 0; i < groupList.size(); i++) {
                    if (groupList.get(i).getGroup_id() == Constants.GROUP_PHONE
                            || groupList.get(i).getGroup_id() == Constants.GROUP_SIM
                            || groupList.get(i).getGroup_id() == Constants.GROUP_CARD)
                        result.add(groupList.get(i));
                }
                for (int i = 0; i < group.size(); i++) {
                    String s = group.get(i);
                    String[] d = s.split(" ");
                    result.add(new Group(Long.parseLong(d[0]), d[1]));
                }
                binding.getNewOrEditContactViewModel().setGroupList(result);
                binding.groupContent.setText(binding.getNewOrEditContactViewModel().getGroupNamesWithData());
                Log.d("123", binding.getNewOrEditContactViewModel().getGroupNamesWithData());
                Log.d("123", binding.getNewOrEditContactViewModel().getGroupList().size() + "");
            }
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI); //获取用户选择的铃声数据
//            String title = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_TITLE);
////        myUriStr = pickedUri.toString();
//            Log.d("123", pickedUri.toString());
//            Log.d("123", title + " 123");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void setPresenter(NewOrEditContactContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showResult() {

    }
}
