package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.BindingItem;

public class GroupItemViewModel extends BindingItem {

    private Group group;
    private Long groupMemberSum;

    public GroupItemViewModel(Group group, Long groupMemberSum) {
        this.group = group;
        this.groupMemberSum = groupMemberSum;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getNumberString(){
        return groupMemberSum + " 个人";
    }

    public Long getGroupMemberSum() {
        return groupMemberSum;
    }

    public void setGroupMemberSum(Long groupMemberSum) {
        this.groupMemberSum = groupMemberSum;
    }

    @Override
    public int getViewType() {
        return R.layout.item_group;
    }

    @Override
    public int getViewVariableId() {
        return BR.GroupItemViewModel;
    }
}
