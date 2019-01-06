package com.dugu.addressbook.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dugu.addressbook.AddressBookApplication;

public class BindingUtil {

    //绑定联系人详情的背景图片
    @BindingAdapter({"image_data", "seed"})
    public static void loadImage(ImageView view, byte[] data, long seed) {
        if (data != null && data.length > 0) {
            Glide.with(AddressBookApplication.getAppContext()).load(data)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(view);
        } else {
            view.setBackgroundColor(AppUtil.getRandomColor(seed));
        }
    }
}
