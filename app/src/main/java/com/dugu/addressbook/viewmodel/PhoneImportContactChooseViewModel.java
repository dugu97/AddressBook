package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.item.PhoneImportContactChooseItemViewModel;

import java.util.List;

public class PhoneImportContactChooseViewModel extends BindingItem {

    private List<PhoneImportContactChooseItemViewModel> viewModels;

    public PhoneImportContactChooseViewModel(List<PhoneImportContactChooseItemViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    public List<PhoneImportContactChooseItemViewModel> getViewModels() {
        return viewModels;
    }

    public void setViewModels(List<PhoneImportContactChooseItemViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_phone_import_contact_choose;
    }

    @Override
    public int getViewVariableId() {
        return BR.PhoneImportContactChooseViewModel;
    }
}
