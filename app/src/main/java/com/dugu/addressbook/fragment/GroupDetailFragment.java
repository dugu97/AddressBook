package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.GroupContactChooseActivity;
import com.dugu.addressbook.activity.ContactDetailActivity;
import com.dugu.addressbook.adapter.GroupDetailAdapter;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.GroupDetailContract;
import com.dugu.addressbook.databinding.FragGroupDetailBinding;
import com.dugu.addressbook.util.AppUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

public class GroupDetailFragment extends BaseFragment implements GroupDetailContract.Ui {

    private GroupDetailContract.Presenter presenter;
    private FragGroupDetailBinding binding;
    private GroupDetailAdapter adapter;
    private Long group_id;

    public static GroupDetailFragment newInstance(Bundle bundle) {
        GroupDetailFragment fragment = new GroupDetailFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_group_detail, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        Intent intent = getActivity().getIntent();
        group_id = intent.getLongExtra(Constants.GROUPACTIVITY_GROUP_ID, -1);
        String group_name = intent.getStringExtra(Constants.GROUPACTIVITY_GROUP_NAME);
        getABActionBar().setTitleText(group_name);

        adapter = new GroupDetailAdapter();
        binding.contactList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.contactList.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        presenter.setGroupId(group_id);
        presenter.start();
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

            }
        });

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long contact_id = binding.getGroupDetailViewModel().getViewModels().get(position).getContact().getContact_id();
                Intent intent = new Intent(getContext(), ContactDetailActivity.class);
                intent.putExtra(Constants.MAINACTIVITY_CONTACT_ID, contact_id);
                startActivity(intent);
            }
        });

        binding.addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GroupContactChooseActivity.class);
                intent.putExtra(Constants.ALLACTIVITY_GROUP_ID, binding.getGroupDetailViewModel().getGroup().getGroup_id());

                //contact选择模式
                intent.putExtra(Constants.MODE_CONTACTCHOOSE_ACTIVITY, Constants.CONTACT_MODE_WITHOUT_GROUP);

                startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE_CONTACT);
            }
        });

        binding.moreOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.start();

        if (adapter.getItemCount() == 0){
            binding.title.setVisibility(View.GONE);
            binding.noContact.setVisibility(View.VISIBLE);
        }else{
            binding.title.setVisibility(View.VISIBLE);
            binding.noContact.setVisibility(View.GONE);
        }
    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity())
                .setItems(Constants.GROUP_DETAIL_MORE_OPERATION_PROJECT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == Constants.GROUP_DETAIL_MORE_OPERATION_DELETE_CONTACT) {
                            //移除成员
                            Intent intent = new Intent(getContext(), GroupContactChooseActivity.class);
                            intent.putExtra(Constants.ALLACTIVITY_GROUP_ID, binding.getGroupDetailViewModel().getGroup().getGroup_id());

                            //contact选择模式
                            intent.putExtra(Constants.MODE_CONTACTCHOOSE_ACTIVITY, Constants.CONTACT_MODE_WITHIN_GROUP);

                            startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE_CONTACT);
                            dialog.dismiss();
                        } else if (which == Constants.GROUP_DETAIL_MORE_OPERATION_RENAME_GROUP) {
                            //重命名
                            showInputDialog();
                            dialog.dismiss();
                        }
                    }
                }).create();
        alertDialog.show();
    }

    private void showInputDialog() {
        new TDialog.Builder(getActivity().getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_input)    //设置弹窗展示的xml布局
//                .setDialogView(view)  //设置弹窗布局,直接传入View
//                .setWidth(600)  //设置弹窗宽度(px)
//                .setHeight(800)  //设置弹窗高度(px)
                .setScreenWidthAspect(getContext(), 0.9f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
//                .setScreenHeightAspect(getContext(), 0.3f)  //设置弹窗高度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置
                .setTag("DialogTest")   //设置Tag
                .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(false)     //弹窗在界面外是否可以点击取消
//                .setDialogAnimationRes(R.style.animate_dialog)  //设置弹窗动画
                .setOnDismissListener(new DialogInterface.OnDismissListener() { //弹窗隐藏时回调方法
                    @Override
                    public void onDismiss(DialogInterface dialog) {
//                        Toast.makeText(DiffentDialogActivity.this, "弹窗消失回调", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnBindViewListener(new OnBindViewListener() {   //通过BindViewHolder拿到控件对象,进行修改
                    @Override
                    public void bindView(BindViewHolder bindViewHolder) {
                        bindViewHolder.setText(R.id.dialog_common_title, "群组重命名");
                        bindViewHolder.setText(R.id.dialog_common_left, "取消");
                        bindViewHolder.setText(R.id.dialog_common_right, "确定");
                        EditText editText = bindViewHolder.getView(R.id.dialog_common_content);
                        InputFilter[] filters = {new InputFilter.LengthFilter(Constants.LABEL_MAX_LENGTH)};
                        editText.setFilters(filters);
                        editText.setHint("群组最长为6个字符");
                    }
                })
                .addOnClickListener(R.id.dialog_common_left, R.id.dialog_common_right)   //添加进行点击控件的id
                .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.dialog_common_left:
                                tDialog.dismiss();
                                break;
                            case R.id.dialog_common_right:
                                EditText editText = viewHolder.getView(R.id.dialog_common_content);
                                String temp = editText.getText().toString().trim();
                                if (!AppUtil.isNullString(temp)) {
                                    presenter.renameGroup(temp);
                                    getABActionBar().setTitleText(temp);
                                } else {
                                    makeToast("群组名为空白字符串");
                                }
                                tDialog.dismiss();
                                break;
                        }
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return false;
                    }
                })
                .create()   //创建TDialog
                .show();    //展示

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_CODE_OK)
            if (requestCode == Constants.REQUEST_CODE_CHOOSE_CONTACT) {
                presenter.setGroupId(binding.getGroupDetailViewModel().getGroup().getGroup_id());
                presenter.start();
            }
    }

    @Override
    public void showResult() {
        binding.setGroupDetailViewModel(presenter.getGroupDetailViewModel());
        adapter.setmItems(binding.getGroupDetailViewModel().getViewModels());
    }

    @Override
    public void setPresenter(GroupDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
