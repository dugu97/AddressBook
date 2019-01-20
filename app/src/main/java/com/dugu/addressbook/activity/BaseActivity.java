package com.dugu.addressbook.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.assembly.LoadingDialog;
import com.dugu.addressbook.listener.OnABToolbarClickLListener;

public abstract class BaseActivity extends AppCompatActivity{
    // 上下文实例
    public Context context;
    // 应用全局的实例
    public AddressBookApplication application;

    public ABToolBar actionBar;

    /**
     * 左边返回按钮事件
     */
    private OnABToolbarClickLListener fallbackClickListener;
    /**
     * 右边按钮事件
     */
    private OnABToolbarClickLListener rightButtonClickListener;

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

    protected abstract int setDuguActionBarId();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        context = getApplicationContext();
        application = (AddressBookApplication) this.getApplication();

        //透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            //基于Toolbar的特别配置 xml配置属性置于根布局则不需要
//            setSupportActionBar(getToolBar().getToolbar());
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        //创建ActionBar
        createActionBar();
        initViews();
        initData();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AddressBookApplication.setmCurActivity(this);
    }

    private void createActionBar(){
            actionBar =  findViewById(setDuguActionBarId());
            if (actionBar != null)
                actionBar.addDuguToolBarCallBack(addDuguActionBarCallBack());
    }

    /**
     * 增加导航栏回调点击事件
     *
     * @return
     */
    protected ABToolBar.ABToolBarCallBack addDuguActionBarCallBack() {
        return new ABToolBar.ABToolBarCallBack() {
            @Override
            public void onFallbackClickCallBack() {
                if (fallbackClickListener == null) {
                    finish();
                } else {
                    fallbackClickListener.onClick();
                }
            }

            @Override
            public void onRightButtonClickCallBack() {
                if (rightButtonClickListener == null) {
                    makeToast("没设置");
                } else {
                    rightButtonClickListener.onClick();
                }
            }
        };
    }

    /**
     * 设置左边返回按钮事件
     */
    public void setFallbackClickListener(OnABToolbarClickLListener fallbackClickListener) {
        this.fallbackClickListener = fallbackClickListener;
    }

    /**
     * 设置右边按钮事件
     */
    public void setRightButtonClickListener(OnABToolbarClickLListener rightButtonClickListener) {
        this.rightButtonClickListener = rightButtonClickListener;
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

    @Nullable
    public ABToolBar getToolBar() {
        return actionBar;
    }
}
