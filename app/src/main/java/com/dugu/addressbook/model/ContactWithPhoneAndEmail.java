package com.dugu.addressbook.model;

import java.util.List;

/*
    用于VCard导入数据整合 用于导入系统联系人数据整合
 */
public class ContactWithPhoneAndEmail {

    private Long contact_id;
    private byte[] icon;   //头像
    private String name;
    private String nickname;
    private String organization;
    private String job;

    private String remark;
    private String address;
    private String birthday;
    private byte[] businessardData;  //二进制名片图片

    private List<String> phoneList;
    private List<String> emailList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public byte[] getBusinessardData() {
        return businessardData;
    }

    public void setBusinessardData(byte[] businessardData) {
        this.businessardData = businessardData;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    @Override
    public String toString() {
        return name + nickname + organization + job + remark + address + birthday + " phoneList:" + phoneList.size() + " emailList:" + emailList.size();
    }
}
