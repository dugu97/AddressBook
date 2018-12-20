package com.dugu.addressbook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.activity.BaseActivity;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.listener.OnKeyDownListener;

/**
 * 继承基类着需提供：newInstance()方法
 */
public abstract class BaseFragment extends Fragment implements OnKeyDownListener {

    protected BaseActivity mActivity;
    private long curClickTimeMillis = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof BaseActivity) {
            mActivity = (BaseActivity) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(initFragment(inflater, container, savedInstanceState));
    }

    private View init(View view) {
        initViews(view);
        initData();
        addListener();
        return view;
    }

    protected abstract View initFragment(LayoutInflater inflater, ViewGroup container,
                                         Bundle savedInstanceState);

    protected ABToolBar getABActionBar() {
        return mActivity == null ? null : mActivity.getToolBar();
    }

    protected void makeToast(String text) {
        if (mActivity != null) {
            mActivity.makeToast(text);
        }
    }

    /**
     * 初始化组件
     */
    protected abstract void initViews(View rootView);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 添加监听器
     */
    protected abstract void addListener();

    /**
     * 是否点击了多次 小于1秒当作是一次点击了
     **/
    protected boolean isMultiplicationClick() {
        if (curClickTimeMillis == 0) {
            curClickTimeMillis = System.currentTimeMillis();
            return false;
        }
        boolean rs = System.currentTimeMillis() - curClickTimeMillis < 1000;
        curClickTimeMillis = System.currentTimeMillis();
        return rs;
    }

    protected boolean isNull(String value) {
        return value == null || "".equals(value.trim());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

}