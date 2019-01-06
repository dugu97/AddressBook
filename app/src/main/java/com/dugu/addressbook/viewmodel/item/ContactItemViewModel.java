package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.BindingItem;

import java.util.List;

public class ContactItemViewModel extends BindingItem {

    private Long contact_id;
    private byte[] icon;   //头像
    private String name;
    private List<String> phoneList;
    private String organization;
    private String job;
    private String firstPingYin;
    private boolean isSIM;

    //默认头像颜色随机
    private int randomColor;

    public ContactItemViewModel(Long contact_id, byte[] icon, String name, String firstPingYin, boolean isSIM, List<String> phoneList, String organization, String job) {
        this.contact_id = contact_id;
        this.icon = icon;
        this.name = name;
        this.firstPingYin = firstPingYin;
        this.isSIM = isSIM;
        this.phoneList = phoneList;
        this.organization = organization;
        this.job = job;


//        //拼接phone
//        String phone = "";
//        for (int i = 0; i < phoneList.size(); i++) {
//            phone = phone + phoneList.get(i);
//        }
//        String temp = name + organization + job + phone;
//        if (!AppUtil.isNullString(temp))
//            this.firstPingYin = String.valueOf(HanZiToPinYinUtil.getFirstPinyin(temp).charAt(0));
//        if (!this.firstPingYin.matches("[a-zA-Z]"))
//            this.firstPingYin = "#";

        randomColor = AppUtil.getRandomColor(contact_id);
    }

    public String getNameOrPhone() {
        if (!AppUtil.isNullString(name))
            return name;
        else if (!phoneList.isEmpty())
            return phoneList.get(0);
        else
            return "（无姓名）";
    }

    public boolean needHideSIMIcon() {
        return !isSIM;
    }

    public boolean needHideRightIcon() {
        //contact_id < 0 为工具栏
        if (contact_id < 0)
            return false;
        return true;
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

    public List<String> getPhone() {
        return phoneList;
    }

    public void setPhone(List<String> phoneList) {
        this.phoneList = phoneList;
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

    public List<String> getPhoneList() {
        return phoneList;
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
