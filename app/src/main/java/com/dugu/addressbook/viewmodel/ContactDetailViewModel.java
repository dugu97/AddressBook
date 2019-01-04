package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Email;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.util.AppUtil;

import java.util.List;

public class ContactDetailViewModel extends BindingItem {

    private Long contact_id;

    private byte[] icon;   //头像
    private byte[] businessardData; //二进制名片图片

    private String name;
    private String organization;
    private String job;
    private String nickname;
    private String address;
    private String birthday;

    private List<String> groupList;
    private List<Phone> phoneList;
    private List<Email> emailList;

    private String ring;
    private String remark;


    //默认头像颜色随机(基于contact_id)
    private int randomColor;

    public ContactDetailViewModel(Long contact_id, byte[] icon, byte[] businessardData, String name, String organization, String job, String nickname, String address, String birthday, java.util.List<String> groupList, java.util.List<Phone> phoneList, java.util.List<Email> emailList, String ring, String remark) {
        this.contact_id = contact_id;
        this.icon = icon;
        this.businessardData = businessardData;
        this.name = name;
        this.organization = organization;
        this.job = job;
        this.nickname = nickname;
        this.address = address;
        this.birthday = birthday;
        this.groupList = groupList;
        this.phoneList = phoneList;
        this.emailList = emailList;
        this.ring = ring;
        this.remark = remark;

        randomColor = AppUtil.getRandomColor(contact_id);
    }

    public String getNameOrPhone() {
        if (!AppUtil.isNullString(name))
            return name;
        else if (!phoneList.isEmpty())
            return phoneList.get(0).getPhone();
        else
            return "（无姓名）";
    }

    public boolean needHideOrganization() {
        if (AppUtil.isNullString(organization))
            return true;
        return false;
    }

    public boolean needHideJob() {
        if (AppUtil.isNullString(job))
            return true;
        return false;
    }

    public Long getContact_id() {
        return contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public byte[] getBusinessardData() {
        return businessardData;
    }

    public void setBusinessardData(byte[] businessardData) {
        this.businessardData = businessardData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public java.util.List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(java.util.List<String> groupList) {
        this.groupList = groupList;
    }

    public java.util.List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(java.util.List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public java.util.List<Email> getEmailList() {
        return emailList;
    }

    public void setEmialList(java.util.List<Email> emialList) {
        emialList = emailList;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRandomColor() {
        return randomColor;
    }

    public void setRandomColor(int randomColor) {
        this.randomColor = randomColor;
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
