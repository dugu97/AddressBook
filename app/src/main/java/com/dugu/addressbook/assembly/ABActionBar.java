package com.dugu.addressbook.assembly;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dugu.addressbook.R;
import com.dugu.addressbook.databinding.AddressbookActionBarBinding;


/**
 * Created by king on 2017/7/5.
 */

public class ABActionBar extends LinearLayout {
    private String TAG = "ABActionBar";

    private AddressbookActionBarBinding binding;

    private ABActionBarCallBack mBarCallBack;

    public ABActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ABActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ABActionBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode())
            return;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.addressbook_action_bar, this, true);

        binding.actionBarFallbackParent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBarCallBack == null) {
                    Toast.makeText(getContext(), "未设置", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    mBarCallBack.onFallbackClickCallBack(v);
                }
            }
        });
        binding.actionBarRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBarCallBack == null) {
                    Toast.makeText(getContext(), "未设置", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    mBarCallBack.onRightButtonClickCallBack(v);
                }
            }
        });
    }


    public void addDuguActionBarCallBack(ABActionBarCallBack callBack) {
        this.mBarCallBack = callBack;
    }


    /**
     * 设置bar为对应颜色
     *
     * @param color
     */
    public void setActionBarBackground(String color) {
        binding.actionBarLayout.setBackgroundColor(Color.parseColor(color));
    }

    /**
     * 设置title字体为对应颜色
     *
     * @param color
     */
    public void setTitleColor(String color) {
        binding.actionBarTitle.setBackgroundColor(Color.parseColor(color));
    }

    /**
     * 设置返回按钮是否可见
     *
     * @param visibility
     */
    public void setFallbackVisibility(int visibility) {
        binding.actionBarFallback.setVisibility(visibility);
        binding.actionBarFallbackParent.setVisibility(visibility);
    }


    /**
     * 设置右按钮是否可见
     *
     * @param visibility
     */
    public void setRightButtonVisibility(int visibility) {
        binding.actionBarRightButton.setVisibility(visibility);
    }

    /**
     * 设置标题是否可见
     *
     * @param visibility
     */
    public void setTitleVisibility(int visibility) {
        binding.actionBarTitle.setVisibility(visibility);
    }

    /**
     * setText(Html.fromHtml(text));
     *
     * @param text
     */
    public void setTitleText(String text) {
        if (text != null && !"".equals(text))
            binding.actionBarTitle.setText(text);
    }

    public View getTitleView() {
        return binding.actionBarTitle;
    }

    public View getFallbackView() {
        return binding.actionBarFallback;
    }

    public View getRightButtonView() {
        return binding.actionBarRightButton;
    }


    public interface ABActionBarCallBack {
        void onFallbackClickCallBack(View view);

        void onRightButtonClickCallBack(View view);
    }
}
