package com.dugu.addressbook.listener;

import android.view.KeyEvent;

/**
 * key的点击事件
 */

public interface OnKeyDownListener {
    boolean onKeyDown(int keyCode, KeyEvent event);
}
