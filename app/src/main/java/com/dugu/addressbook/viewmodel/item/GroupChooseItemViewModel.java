package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.BindingItem;

public class GroupChooseItemViewModel extends BindingItem {

    private Group group;
    private boolean isChecked;

    public GroupChooseItemViewModel(Group group, boolean isChecked) {
        this.group = group;
        this.isChecked = isChecked;
    }

    public String getGroupName(){
        return group.getGroup_name();
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int getViewType() {
        return R.layout.item_group_choose;
    }

    @Override
    public int getViewVariableId() {
        return BR.GroupChooseItemViewModel;
    }
}
