package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.util.HanZiToPinYinUtil;

public class ContactItemViewModel extends BindingItem {

    private Long contact_id;
    private byte[] icon;   //头像
    private String name;
    private String phone;
    private String organization;
    private String job;
    private String firstPingYin;

    //头像颜色随机
    private int randomColor;

    public ContactItemViewModel(Long contact_id, byte[] icon, String name, String phone, String organization, String job) {
        this.contact_id = contact_id;
        this.icon = icon;
        this.name = name;
        this.phone = phone;
        this.organization = organization;
        this.job = job;

        String temp = name + organization + job + phone;
        if (!AppUtil.isNullString(temp))
            this.firstPingYin = String.valueOf(HanZiToPinYinUtil.getFirstPinyin(temp).charAt(0));
        if (!this.firstPingYin.matches("[a-zA-Z]"))
            this.firstPingYin = "#";

        randomColor = AppUtil.getRandomColor();
    }

    // 用于二级页面的ViewModel生成（contact_id < 0）
    public ContactItemViewModel(Long contact_id, byte[] icon, String name, String phone, String organization, String job, String firstPingYin) {
        this.contact_id = contact_id;
        this.icon = icon;
        this.name = name;
        this.phone = phone;
        this.organization = organization;
        this.job = job;
        this.firstPingYin = firstPingYin;
    }

    public String getNameOrPhone() {
        if (!AppUtil.isNullString(name))
            return name;
        else if (!AppUtil.isNullString(phone))
            return phone;
        else
            return "（无姓名）";
    }

    public String getOrganizationOrJob() {
        if (!AppUtil.isNullString(organization) && !AppUtil.isNullString(job))
            return organization + "  " + job;
        else if (!AppUtil.isNullString(organization))
            return organization;
        else if (!AppUtil.isNullString(job))
            return job;
        else
            return "";
    }

    public boolean needHideOrganizationAndJob() {
        if (AppUtil.isNullString(organization) && AppUtil.isNullString(job))
            return true;
        return false;
    }

    public void setRandomColor() {
        this.randomColor = randomColor;
    }

    public int getRandomColor() {
        return randomColor;
    }

    public Long getContact_id() {
        return contact_id;
    }

    public String getFirstPingYin() {
        return firstPingYin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public int getViewType() {
        return R.layout.item_contact_list;
    }

    @Override
    public int getViewVariableId() {
        return BR.contact;
    }
}
