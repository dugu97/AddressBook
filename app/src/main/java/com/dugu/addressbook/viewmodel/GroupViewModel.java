package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.item.GroupItemViewModel;

import java.util.List;

public class GroupViewModel extends BindingItem {

    private List<GroupItemViewModel> groupItemViewModels;
    private List<Group> deleteGroup;

    public GroupViewModel(List<GroupItemViewModel> groupItemViewModels) {
        this.groupItemViewModels = groupItemViewModels;
    }

    public List<GroupItemViewModel> getGroupItemViewModels() {
        return groupItemViewModels;
    }

    public void setGroupItemViewModels(List<GroupItemViewModel> groupItemViewModels) {
        this.groupItemViewModels = groupItemViewModels;
    }

    public List<Group> getDeleteGroup() {
        return deleteGroup;
    }

    public void setDeleteGroup(List<Group> deleteGroup) {
        this.deleteGroup = deleteGroup;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_group;
    }

    @Override
    public int getViewVariableId() {
        return BR.GroupViewModel;
    }
}
