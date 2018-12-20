package com.dugu.addressbook.assembly;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.R;
import com.dugu.addressbook.databinding.AddressbookToolBarBinding;

public class ABToolBar extends LinearLayout {

    AddressbookToolBarBinding binding;

    private ABToolBarCallBack mBarCallBack;

    public ABToolBar(Context context) {
        super(context);
        init(context);
    }

    public ABToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ABToolBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode())
            return;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.addressbook_tool_bar, this, true);

//        binding.toolbar.inflateMenu(R.menu.navi_qr_code_icon);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBarCallBack == null) {
                    Toast.makeText(AddressBookApplication.getAppContext(), "未设置", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    mBarCallBack.onFallbackClickCallBack();
                }
            }
        });

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mBarCallBack == null) {
                    Toast.makeText(AddressBookApplication.getAppContext(), "未设置", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    mBarCallBack.onRightButtonClickCallBack();
                }
                return true;
            }
        });
    }

    public void addDuguToolBarCallBack(ABToolBarCallBack callBack) {
        this.mBarCallBack = callBack;
    }

    public interface ABToolBarCallBack {
        void onFallbackClickCallBack();

        void onRightButtonClickCallBack();
    }

    public void setCenterTitleVisiblity(int visiblity) {
        binding.title.setVisibility(visiblity);
    }

    public void setCenterTitleText(String titleText) {
        binding.title.setText(titleText);
    }

    public void setTitleText(String titleText) {
        binding.toolbar.setTitle(titleText);
    }

    public void setNavigationIcon(int drawableId) {
        binding.toolbar.setNavigationIcon(drawableId);
    }

    public Toolbar getToolbar(){
        return binding.toolbar;
    }
}
