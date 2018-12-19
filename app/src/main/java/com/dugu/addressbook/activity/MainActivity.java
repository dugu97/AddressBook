package com.dugu.addressbook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dugu.addressbook.fragment.MainFragment;
import com.dugu.addressbook.presenter.ContactsPresenter;

public class MainActivity extends SingleFragmentActivity{

    private ContactsPresenter presenter;

    @Override
    protected Fragment createFragment(Bundle bundle) {
        MainFragment fragment = MainFragment.newInstance(bundle);
        //创建Presenter
        presenter = new ContactsPresenter(fragment);
        return fragment;
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
//        //透明状态栏
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            int statusBarHeight = getStatusBarHeight(this.getBaseContext());
//            view.setPadding(0, statusBarHeight, 0, 0);
//        }
    }

//    public int getStatusBarHeight(Context context) {
//        int result = 0;
//        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
//                "android");
//        if (resourceId > 0) {
//            result = context.getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }

    @Override
    protected void initViews() {
//        getABActionBar().setVisibility(View.GONE);
        getABActionBar().setRightButtonVisibility(View.VISIBLE);
        getABActionBar().setFallbackVisibility(View.GONE);
        getABActionBar().setTitleText("通讯录");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected int setDuguActionBarId() {
        return super.setDuguActionBarId();
    }
}
