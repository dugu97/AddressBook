package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.item.GroupChooseItemViewModel;

import java.util.List;

public class GroupChooseViewModel extends BindingItem{


    private List<GroupChooseItemViewModel> groupListViewModel;

    public GroupChooseViewModel(List<GroupChooseItemViewModel> groupListViewModel) {
        this.groupListViewModel = groupListViewModel;
    }

    public List<GroupChooseItemViewModel> getGroupListViewModel() {
        return groupListViewModel;
    }

    public void setGroupListViewModel(List<GroupChooseItemViewModel> groupListViewModel) {
        this.groupListViewModel = groupListViewModel;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_group_choose;
    }

    @Override
    public int getViewVariableId() {
        return BR.GroupChooseViewModel;
    }
}
