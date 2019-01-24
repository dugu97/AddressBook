package com.dugu.addressbook.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.R;
import com.dugu.addressbook.contract.ImportAndExportContract;
import com.dugu.addressbook.databinding.FragImportAndExportBinding;
import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.util.MobileContactUtil;
import com.dugu.addressbook.util.VCardUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.base.TBaseAdapter;
import com.timmy.tdialog.list.TListDialog;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ImportAndExportFragment extends BaseFragment implements ImportAndExportContract.Ui {

    private ImportAndExportContract.Presenter presenter;
    private FragImportAndExportBinding binding;

    public static ImportAndExportFragment newInstance(Bundle bundle) {
        ImportAndExportFragment fragment = new ImportAndExportFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_import_and_export, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void initData() {
        presenter.start();
    }

    @Override
    protected void addListener() {

        binding.importVCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("正在根目录搜索vcf文件...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> fileList = AppUtil.getFileList(Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                        for (int i = 0; i < fileList.size(); i++) {
                            Log.d("123", fileList.get(i));
                        }
                        closeLoadingDialog();
                        showListDialog(fileList);
                    }
                }).start();
            }
        });

        binding.exportVCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportAndExportFragmentPermissionsDispatcher.requestPermissionAndShowDialogWithPermissionCheck(ImportAndExportFragment.this);
            }
        });

        binding.importSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("正在导入手机联系人...");
                ImportAndExportFragmentPermissionsDispatcher.requestPermissionWithReadContactWithPermissionCheck(ImportAndExportFragment.this);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImportAndExportFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    public void requestPermissionWithReadContact(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        presenter.importContact(MobileContactUtil.getAllContactWithSim(getContext()));
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }finally {
                        closeLoadingDialog();
                    }
                }
            }).start();
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void requestPermissionAndShowDialog() {
        showConfirmDialog(AppUtil.formatFileNameWithTime(System.currentTimeMillis()));
    }

    private void showListDialog(final List<String> strings) {

        List<String> showString = new ArrayList<>();

        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            showString.add(s.substring(s.lastIndexOf("/") + 1, s.length()));
        }

        new TListDialog.Builder(getActivity().getSupportFragmentManager())
                .setScreenWidthAspect(getActivity(), 0.95f)
                .setGravity(Gravity.CENTER)
                .setListLayoutRes(R.layout.dialog_list_recycle, LinearLayoutManager.VERTICAL)
                .setAdapter(new TBaseAdapter<String>(R.layout.item_simple_text, showString) {

                    @Override
                    protected void onBind(BindViewHolder holder, int position, String s) {
                        holder.setText(R.id.tv, s);
                    }
                })
                .setOnAdapterItemClickListener(new TBaseAdapter.OnAdapterItemClickListener<String>() {
                    @Override
                    public void onItemClick(BindViewHolder holder, int position, String s, TDialog tDialog) {
                        showLoadingDialog("正在导入中...");
                        try {
                            List<ContactWithPhoneAndEmail> models = VCardUtil.parseVCard(new File(strings.get(position)));
                            if (models.size() > 0)
                                presenter.importContact(models);
                            else
                                makeToast("VCard格式不支持");
                        } catch (IOException e) {
                            e.printStackTrace();
                            makeToast("导入出错");
                        }finally {
                            closeLoadingDialog();
                            tDialog.dismiss();
                        }
                    }
                })
                .create()
                .show();

    }

    private void showConfirmDialog(final String fileName) {
        new TDialog.Builder(getActivity().getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_confirm)    //设置弹窗展示的xml布局
                .setScreenWidthAspect(getContext(), 0.9f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置
                .setTag("DialogTest")   //设置Tag
                .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(false)     //弹窗在界面外是否可以点击取消
                .setOnDismissListener(new DialogInterface.OnDismissListener() { //弹窗隐藏时回调方法
                    @Override
                    public void onDismiss(DialogInterface dialog) {
//                        Toast.makeText(DiffentDialogActivity.this, "弹窗消失回调", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnBindViewListener(new OnBindViewListener() {   //通过BindViewHolder拿到控件对象,进行修改
                    @Override
                    public void bindView(BindViewHolder bindViewHolder) {
                        bindViewHolder.setText(R.id.dialog_common_title, "导出联系人");
                        bindViewHolder.setText(R.id.dialog_common_left, "取消");
                        bindViewHolder.setText(R.id.dialog_common_right, "导出");
                        bindViewHolder.setText(R.id.dialog_common_content, "是否将联系人列表导出至 内部存储/" + fileName + "？ 导出后，请妥善保管您的联系人信息。");
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
                                tDialog.dismiss();
                                showLoadingDialog("正在导出中...");
                                if (!binding.getImportAndExportViewModel().getContacts().isEmpty()) {
                                    try {
                                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
                                        VCardUtil.createVCard(binding.getImportAndExportViewModel().getContacts(), file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        closeLoadingDialog();
                                    }
                                } else {
                                    makeToast("联系人列表为空");
                                    closeLoadingDialog();
                                }
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
    public void showResult() {
        binding.setImportAndExportViewModel(presenter.getImportAndExportViewModel());
    }

    @Override
    public void setPresenter(ImportAndExportContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
