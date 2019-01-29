package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.List;

public class BusinessCardViewModel extends BindingItem {

    private List<ContactItemViewModel> viewModels;

    public BusinessCardViewModel(List<ContactItemViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    public List<ContactItemViewModel> getViewModels() {
        return viewModels;
    }

    public void setViewModels(List<ContactItemViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_business_card;
    }

    @Override
    public int getViewVariableId() {
        return BR.BusinessCardViewModel;
    }
}
