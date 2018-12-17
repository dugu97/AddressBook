package com.dugu.addressbook.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.assembly.ABActionBar;

public abstract class BaseActivity extends AppCompatActivity{
    // 上下文实例
    public Context context;
    // 应用全局的实例
    public AddressBookApplication application;

    public ABActionBar actionBar;
    /**
     * 左边返回按钮事件
     */
    private View.OnClickListener fallbackClickListener;
    /**
     * 右边按钮事件
     */
    private View.OnClickListener rightButtonClickListener;

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
                actionBar.addDuguActionBarCallBack(addDuguActionBarCallBack());
    }

    /**
     * 增加导航栏回调点击事件
     *
     * @return
     */
    protected ABActionBar.ABActionBarCallBack addDuguActionBarCallBack() {
        return new ABActionBar.ABActionBarCallBack() {
            @Override
            public void onFallbackClickCallBack(View view) {
                if (fallbackClickListener == null) {
                    finish();
                } else {
                    fallbackClickListener.onClick(view);
                }
            }

            @Override
            public void onRightButtonClickCallBack(View view) {
                if (rightButtonClickListener == null) {
                    makeToast("没设置");
                } else {
                    rightButtonClickListener.onClick(view);
                }
            }
        };
    }

    /**
     * 设置左边返回按钮事件
     */
    public void setFallbackClickListener(View.OnClickListener fallbackClickListener) {
        this.fallbackClickListener = fallbackClickListener;
    }

    /**
     * 设置右边按钮事件
     */
    public void setRightButtonClickListener(View.OnClickListener rightButtonClickListener) {
        this.rightButtonClickListener = rightButtonClickListener;
    }

    /**
     * Toast打印
     */
    public void makeToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    public ABActionBar getABActionBar() {
        return actionBar;
    }
}
