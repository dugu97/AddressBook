package com.dugu.addressbook.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.R;

public class BindingUtil {
    @BindingAdapter({"image_data"})
    public static void loadImage(ImageView view, byte[] data) {
        if (data != null && data.length > 0) {
            Glide.with(AddressBookApplication.getAppContext()).load(data)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(view);
        } else {
            view.setImageResource(R.drawable.vector_drawable_contact_default_icon);
        }
    }
}
