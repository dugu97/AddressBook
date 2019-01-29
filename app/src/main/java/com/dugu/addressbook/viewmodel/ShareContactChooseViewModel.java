package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.item.ContactChooseItemViewModel;

import java.util.List;

public class ShareContactChooseViewModel extends BindingItem {

    private List<ContactChooseItemViewModel> viewModels;

    public ShareContactChooseViewModel(List<ContactChooseItemViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    public List<ContactChooseItemViewModel> getViewModels() {
        return viewModels;
    }

    public void setViewModels(List<ContactChooseItemViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_share_contact_choose;
    }

    @Override
    public int getViewVariableId() {
        return BR.ShareContactChooseViewModel;
    }
}
