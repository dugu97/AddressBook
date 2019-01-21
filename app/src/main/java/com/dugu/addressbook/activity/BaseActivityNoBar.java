package com.dugu.addressbook.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.assembly.LoadingDialog;

public abstract class BaseActivityNoBar extends AppCompatActivity {
    // 上下文实例
    public Context context;
    // 应用全局的实例
    public AddressBookApplication application;

    /**
     * 初始化组件
     */
    protected abstract void initViews();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 添加监听器
     */
    protected abstract void addListener();


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        context = getApplicationContext();
        application = (AddressBookApplication) this.getApplication();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        initViews();
        initData();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AddressBookApplication.setmCurActivity(this);
    }

    /**
     * Toast打印
     */
    public void makeToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载界面
     */
    private LoadingDialog loadingDialog;

    /**
     * 显示加载框
     *
     * @param message
     */
    public void showLoadingDialog(String message) {
        if (loadingDialog != null && loadingDialog.getDialog() != null && loadingDialog.getDialog().isShowing()) {
            loadingDialog.dismiss();
            return;
        }
        if (loadingDialog != null && loadingDialog.getDialog() != null && !loadingDialog.getDialog().isShowing()) {
            loadingDialog.setMessage(message);
            loadingDialog.show(getSupportFragmentManager(), "loadingDialog");
            return;
        }
        if (loadingDialog == null || loadingDialog.getDialog() == null) {
            loadingDialog = new LoadingDialog();
            loadingDialog.setMessage(message);
            loadingDialog.show(getSupportFragmentManager(), "loadingDialog");
        }
    }

    /**
     * 关闭加载框
     */
    public void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
