package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.item.ShareContactChooseItemViewModel;

import java.util.List;

public class ShareContactChooseViewModel extends BindingItem {

    private List<ShareContactChooseItemViewModel> viewModels;

    public ShareContactChooseViewModel(List<ShareContactChooseItemViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    public List<ShareContactChooseItemViewModel> getViewModels() {
        return viewModels;
    }

    public void setViewModels(List<ShareContactChooseItemViewModel> viewModels) {
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
