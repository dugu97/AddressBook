package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.item.GroupDetailItemViewModel;

import java.util.List;

public class GroupDetailViewModel extends BindingItem {

    private Group group;
    private List<GroupDetailItemViewModel> viewModels;

    public GroupDetailViewModel(Group group, List<GroupDetailItemViewModel> viewModels) {
        this.group = group;
        this.viewModels = viewModels;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<GroupDetailItemViewModel> getViewModels() {
        return viewModels;
    }

    public void setViewModels(List<GroupDetailItemViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_group_detail;
    }

    @Override
    public int getViewVariableId() {
        return BR.GroupDetailViewModel;
    }
}
