package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.BindingItem;

public class ContactInputItemViewModel extends BindingItem {

    private int sortKey;  //用于分组排序

    private int serialNumber; //用于同组排序

    private String title;
    private String content;


    public ContactInputItemViewModel(int sortKey, int serialNumber, String title, String content) {
        this.sortKey = sortKey;
        this.serialNumber = serialNumber;
        this.title = title;
        this.content = content;
    }

    public boolean needHideShowMoreIcon() {
        // 需要隐藏两个图标
        if (sortKey == Constants.SORTKEY_PHONE || sortKey == Constants.SORTKEY_EMAIL)
            return false;
        return true;
    }

    public int getSortKey() {
        return sortKey;
    }

    public void setSortKey(int sortKey) {
        this.sortKey = sortKey;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content.trim();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHint() {
        if (sortKey == Constants.SORTKEY_PHONE)
            return "电话号码";
        else if (sortKey == Constants.SORTKEY_EMAIL)
            return "电子邮件";
        else if (sortKey == Constants.SORTKEY_NICKNAME)
            return "昵称";
        else if (sortKey == Constants.SORTKEY_ADDRESS)
            return "地址";
        else if (sortKey == Constants.SORTKEY_REMARK)
            return "备注";
        else
            return "";
    }

    @Override
    public int getViewType() {
        return R.layout.item_contact_input;
    }

    @Override
    public int getViewVariableId() {
        return BR.ContactInputItemViewModel;
    }

    @Override
    public String toString() {
        return sortKey+ " " + serialNumber + " " + title + " " + content;
    }
}
