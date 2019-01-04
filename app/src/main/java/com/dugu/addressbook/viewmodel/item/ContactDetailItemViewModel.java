package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.BindingItem;

public class ContactDetailItemViewModel extends BindingItem {

    private int sortKey;

    //标题
    private String title;
    //内容
    private String content;

    public ContactDetailItemViewModel(int sortKey, String title, String content) {
        this.sortKey = sortKey;
        this.title = title;
        this.content = content;
    }

    public int getSortKey() {
        return sortKey;
    }

    public void setSortKey(int sortKey) {
        this.sortKey = sortKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getViewType() {
        return R.layout.item_contact_detail;
    }

    @Override
    public int getViewVariableId() {
        return BR.ContactDetailItemViewModel;
    }
}
