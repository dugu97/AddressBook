package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewOrEditContactViewModel extends BindingItem {

    private int mode;  //新建模式 和 编辑模式

    private Long contact_id;
    private byte[] icon;   //头像

    private String name;
    private String organization;
    private String job;

    private String birthday;
    private List<Group> groupList;

    private List<ContactInputItemViewModel> inputList;  //只有手机联系人需要用到

    public NewOrEditContactViewModel(int mode, Long contact_id, byte[] icon, String name, String organization, String job, List<ContactInputItemViewModel> inputList, List<Group> groupList) {
        this.mode = mode;
        this.contact_id = contact_id;
        this.icon = icon;
        this.name = name;
        this.organization = organization;
        this.job = job;
        this.groupList = groupList;

        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT) {
            setNewPhoneContactDefaultInputListAndGroupList();
        }

    }

    public boolean needHideNewModeTitle() {
        if (mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT || mode == Constants.CONTACT_MODE_EDIT_SMS_CONTACT)
            return true;
        return false;
    }

    public String getModeTitle() {
        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT)
            return "手机联系人";
        else if (mode == Constants.CONTACT_MODE_NEW_SMS_CONTACT)
            return "SIM联系人";
        else
            return "";
    }

    public void setNewPhoneContactDefaultInputListAndGroupList() {
        //新建手机联系人基本输入信息
        inputList = new ArrayList<>();
        inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_PHONE, 1, "手机", ""));
        inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_EMAIL, 2, "私人", ""));
        inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_REMARK, 3, "备注", ""));
        inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_NICKNAME, 4, "昵称", ""));
        inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_ADDRESS, 5, "地址", ""));

        //新建手机联系人初始群组
        groupList = new ArrayList<>();
        groupList.add(new Group((long) Constants.GROUP_PHONE, Constants.GROUP_PROJECT[Constants.GROUP_PHONE]));
    }

    public List<ContactInputItemViewModel> getInputList() {
        return inputList;
    }

    public void setInputList(List<ContactInputItemViewModel> inputList) {
        this.inputList = inputList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public String getGroupNamesWithData(){
        String groups = "";
        //剔除不显示的群组
        for (int i = 1; i < groupList.size(); i++) {
            if (i == 1)
                groups = groupList.get(i).getGroup_name();
            else
                groups = groups + ", " + groupList.get(i).getGroup_name();
        }
        return groups;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    @Override
    public int getViewType() {
        return BR.NewOrEditContactViewModel;
    }

    @Override
    public int getViewVariableId() {
        return R.layout.frag_edit_and_new_contact;
    }
}
