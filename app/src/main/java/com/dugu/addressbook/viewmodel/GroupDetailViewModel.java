package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.item.GroupDetailItemViewModel;

import java.util.List;

public class GroupDetailViewModel extends BindingItem {

    private List<GroupDetailItemViewModel> viewModels;

    public GroupDetailViewModel(List<GroupDetailItemViewModel> viewModels) {
        this.viewModels = viewModels;
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
