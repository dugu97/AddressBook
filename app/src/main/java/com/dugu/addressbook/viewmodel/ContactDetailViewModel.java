package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.util.AppUtil;

import java.util.List;

public class ContactDetailViewModel extends BindingItem {

    private Contact contact;

//    private Long contact_id;
//
//    private byte[] icon;   //头像
//    private byte[] businessardData; //二进制名片图片
//
//    private String name;
//    private String organization;
//    private String job;
//    private String nickname;
//    private String address;
//    private String birthday;
//
//    private List<Group> groupList;
//    private List<Phone> phoneList;
//    private List<Email> emailList;
//
//    private String remark;

    private boolean isInBlackGroup;

    public ContactDetailViewModel(Contact contact) {
        this.contact = contact;
        this.isInBlackGroup = isInBlackGroup();
    }


    public String getNameOrPhoneInTitle(){
        return AppUtil.getContactName(contact);
    }

    public String getNameOrPhone() {
        String temp = AppUtil.getContactName(contact);
        if (isInBlackGroup)
            temp = temp + " (已加入黑名单)";
        return temp;
    }

    private boolean isInBlackGroup() {
        List<Group> groupList = contact.getGroupList();
        for (Group p : groupList
                ) {
            if (p.getGroup_id() == Constants.GROUP_BLACK)
                return true;
        }
        return false;
    }

    public boolean needHideOrganization() {
        if (AppUtil.isNullString(contact.getOrganization()))
            return true;
        return false;
    }

    public boolean needHideJob() {
        if (AppUtil.isNullString(contact.getJob()))
            return true;
        return false;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setInBlackGroup(boolean inBlackGroup) {
        isInBlackGroup = inBlackGroup;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_contact_detail;
    }

    @Override
    public int getViewVariableId() {
        return BR.ContactDetailViewModel;
    }
}
