package com.dugu.addressbook.util;

import android.view.View;

import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.viewmodel.item.ContactDetailItemViewModel;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dugu on 2019/1/11.
 */

public class CommonUtil {
    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 对数据进行排序
     *
     * @param list 要进行排序的数据源
     */
    public static void sortContactItemViewModelData(List<ContactItemViewModel> list) {
        if (list == null || list.size() == 0) return;
        Collections.sort(list, new Comparator<ContactItemViewModel>() {
            @Override
            public int compare(ContactItemViewModel o1, ContactItemViewModel o2) {
                if (o1.getFirstPingYin().equals(o2.getFirstPingYin()))
                    return o1.getContact().getContact_id().compareTo(o2.getContact().getContact_id());
                else
                    return o1.getFirstPingYin().compareTo(o2.getFirstPingYin());
            }
        });
    }

    public static void sortContactlData(List<Contact> list) {
        if (list == null || list.size() == 0) return;
        Collections.sort(list, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getContact_id().compareTo(o2.getContact_id());
            }
        });
    }

    public static void sortContactDetailItemViewModelData(List<ContactDetailItemViewModel> list) {
        if (list == null || list.size() == 0) return;
        Collections.sort(list, new Comparator<ContactDetailItemViewModel>() {
            @Override
            public int compare(ContactDetailItemViewModel o1, ContactDetailItemViewModel o2) {
                return o1.getSortKey() - o2.getSortKey();
            }
        });
    }

    /**
     * @param beans 数据源
     * @return tags 返回一个包含所有Tag字母在内的字符串
     */
    public static String getTags(List<ContactItemViewModel> beans) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < beans.size(); i++) {
            if (!builder.toString().contains(beans.get(i).getFirstPingYin())) {
                builder.append(beans.get(i).getFirstPingYin());
            }
        }
        return builder.toString();
    }
}
