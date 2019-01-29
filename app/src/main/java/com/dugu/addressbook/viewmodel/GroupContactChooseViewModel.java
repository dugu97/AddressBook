package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.item.ContactChooseItemViewModel;

import java.util.List;

public class GroupContactChooseViewModel extends BindingItem {

    private Group group;
    List<ContactChooseItemViewModel> chooseItemViewModels;


    public GroupContactChooseViewModel(Group group, List<ContactChooseItemViewModel> chooseItemViewModels) {
        this.group = group;
        this.chooseItemViewModels = chooseItemViewModels;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<ContactChooseItemViewModel> getChooseItemViewModels() {
        return chooseItemViewModels;
    }

    public void setChooseItemViewModels(List<ContactChooseItemViewModel> chooseItemViewModels) {
        this.chooseItemViewModels = chooseItemViewModels;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_contact_choose;
    }

    @Override
    public int getViewVariableId() {
        return BR.GroupContactChooseViewModel;
    }
}
