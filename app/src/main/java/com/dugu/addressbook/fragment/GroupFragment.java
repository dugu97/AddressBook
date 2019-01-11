package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.GroupDetailActivity;
import com.dugu.addressbook.adapter.recycleview.SimpleAdapter;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.GroupContract;
import com.dugu.addressbook.databinding.FragGroupBinding;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.item.GroupItemViewModel;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

public class GroupFragment extends BaseFragment implements GroupContract.Ui {

    private GroupContract.Presenter presenter;
    private FragGroupBinding binding;
    private SimpleAdapter<GroupItemViewModel> simpleAdapter;

    public static GroupFragment newInstance(Bundle bundle) {
        GroupFragment fragment = new GroupFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_group, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        simpleAdapter = new SimpleAdapter<>(R.layout.item_group, BR.GroupItemViewModel);
        binding.groupList.setAdapter(simpleAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.groupList.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
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

        simpleAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GroupDetailActivity.class);
                Group g = binding.getGroupViewModel().getGroupItemViewModels().get(position).getGroup();
                intent.putExtra(Constants.GROUPACTIVITY_GROUP_ID, g.getGroup_id());
                intent.putExtra(Constants.GROUPACTIVITY_GROUP_NAME, g.getGroup_name());
                startActivity(intent);
            }
        });

        binding.newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    @Override
    public void showResult() {
        binding.setGroupViewModel(presenter.getGroupViewModel());
        simpleAdapter.setmDatas(binding.getGroupViewModel().getGroupItemViewModels());
    }

    @Override
    public void addGroup() {
        binding.setGroupViewModel(presenter.getGroupViewModel());
        simpleAdapter.setmDatas(binding.getGroupViewModel().getGroupItemViewModels());
        simpleAdapter.notifyDataSetChanged();
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
                        bindViewHolder.setText(R.id.dialog_common_title, "新建群组");
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
                                if (!AppUtil.isNullString(temp))
                                    presenter.createGroup(temp);
                                else {
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
    public void setPresenter(GroupContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
