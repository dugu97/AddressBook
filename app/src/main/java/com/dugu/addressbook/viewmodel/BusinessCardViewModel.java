package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;

public class BusinessCardViewModel extends BindingItem {
    @Override
    public int getViewType() {
        return R.layout.frag_business_card;
    }

    @Override
    public int getViewVariableId() {
        return BR.BusinessCardViewModel;
    }
}
