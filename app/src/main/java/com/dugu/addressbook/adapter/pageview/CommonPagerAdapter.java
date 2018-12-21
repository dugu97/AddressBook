package com.dugu.addressbook.adapter.pageview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.viewmodel.BindingItem;

import java.util.List;

/**
 * Created by dugu on 2018/12/21.
 */

public class CommonPagerAdapter<T extends BindingItem> extends PagerAdapter {

    protected List<T> mItems;

    //自己造一个池，可以提高加载效率，与复用率，
    public CommonPagerAdapter(List<T> mItems) {
        this.mItems = mItems;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), mItems.get(position).getViewType(), container, false);
        binding.setVariable(mItems.get(position).getViewVariableId(), mItems.get(position));
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}