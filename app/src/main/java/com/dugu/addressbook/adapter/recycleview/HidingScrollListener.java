package com.dugu.addressbook.adapter.recycleview;

import android.support.v7.widget.RecyclerView;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.util.AppUtil;

public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    //移动多少距离后显示隐藏 (此处应为搜索框的的 hight + marginTop) 见于xml
    private static final int HIDE_THRESHOLD = AppUtil.dp2px(AddressBookApplication.getAppContext(), 35);
    private int scrolledDistance = 0; //移动的中距离
    private boolean controlsVisible = true; //显示或隐藏

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {//移动总距离大于规定距离 并且是显示状态就隐藏
            onHide();
            controlsVisible = false; 
            scrolledDistance = 0;//归零
        }
        else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) { 
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }
        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) { //显示状态向上滑动 或 隐藏状态向下滑动 总距离增加
            scrolledDistance += dy;
        }
 
    }
 
 
    public abstract void onHide();
 
    public abstract void onShow();
}
