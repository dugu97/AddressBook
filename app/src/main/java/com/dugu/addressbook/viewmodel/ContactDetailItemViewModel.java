package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.R;

import java.util.List;

public class ContactDetailItemViewModel extends BindingItem {

    private Long contact_id;
    private Long group_id;
    private byte[] icon;   //头像
    private String name;
    private List<String> phoneList;
    private String organization;
    private String job;
    private String firstPingYin;

    //默认头像颜色随机(基于contact_id)
    private int randomColor;

    @Override
    public int getViewType() {
        return R.layout.item_contact_detail;
    }

    @Override
    public int getViewVariableId() {
        return 0;
    }
}
