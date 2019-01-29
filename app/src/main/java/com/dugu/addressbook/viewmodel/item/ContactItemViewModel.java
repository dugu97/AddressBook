package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.util.HanZiToPinYinUtil;
import com.dugu.addressbook.viewmodel.BindingItem;

public class ContactItemViewModel extends BindingItem {

    private Contact contact;

    private String firstPingYin;

    public ContactItemViewModel(Contact contact) {
        this.contact = contact;

        updateFirstPingYin();
    }

    public String getNameOrPhone() {
        return AppUtil.getContactName(contact);
    }

    public boolean needHideRightIcon() {
        //contact_id < 0 为工具栏
        if (contact.getContact_id() < 0)
            return false;
        return true;
    }

    public String getOrganizationOrJob() {
        if (!AppUtil.isNullString(contact.getOrganization()) && !AppUtil.isNullString(contact.getJob()))
            return contact.getOrganization() + "  " + contact.getJob();
        else if (!AppUtil.isNullString(contact.getOrganization()))
            return contact.getOrganization();
        else if (!AppUtil.isNullString(contact.getJob()))
            return contact.getJob();
        else
            return "";
    }

    public boolean needHideOrganizationAndJob() {
        if (AppUtil.isNullString(contact.getOrganization()) && AppUtil.isNullString(contact.getJob()))
            return true;
        return false;
    }

    private void updateFirstPingYin() {
        //计算firstPingYin
        if (contact.getContact_id() > 0) {
            String phone = "";
            if (contact.getPhoneList() != null)
                for (int i = 0; i < contact.getPhoneList().size(); i++) {
                    phone = phone + contact.getPhoneList().get(i).getPhone();
                }

            String temp = "";
            if (!AppUtil.isNullString(contact.getName()))
                temp = contact.getName();
            if (!AppUtil.isNullString(contact.getOrganization()))
                temp = temp + contact.getOrganization();
            if (!AppUtil.isNullString(contact.getJob()))
                temp = temp + contact.getJob();
            if (!AppUtil.isNullString(phone))
                temp = temp + phone;

            if (!AppUtil.isNullString(temp)) {
                this.firstPingYin = String.valueOf(HanZiToPinYinUtil.getFirstPinyin(temp).charAt(0));
            } else {
                this.firstPingYin = "#";
            }
            if (!this.firstPingYin.matches("[a-zA-Z]"))
                this.firstPingYin = "#";

        } else {
            this.firstPingYin = "#";
        }
    }

    public String getFirstPingYin() {
        return firstPingYin;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setFirstPingYin(String firstPingYin) {
        this.firstPingYin = firstPingYin;
    }


    @Override
    public int getViewType() {
        return R.layout.item_contact_list;
    }

    @Override
    public int getViewVariableId() {
        return BR.ContactItemViewModel;
    }
}
