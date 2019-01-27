package com.dugu.addressbook.presenter;

import com.dugu.addressbook.contract.PhoneImportContactChooseContract;
import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.util.MobileContactUtil;
import com.dugu.addressbook.viewmodel.item.PhoneImportContactChooseItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class PhoneImportContactChoosePresenter implements PhoneImportContactChooseContract.Presenter {

    private final PhoneImportContactChooseContract.Ui mUi;

    public PhoneImportContactChoosePresenter(PhoneImportContactChooseContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void importContact(List<PhoneImportContactChooseItemViewModel> models) {
        List<ContactWithPhoneAndEmail> list = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            PhoneImportContactChooseItemViewModel contact = models.get(i);
            if (contact.isChecked()) {
                list.add(contact.getContactWithPhoneAndEmail());
            }
        }
        MobileContactUtil.importContactWithList(list);
    }


}
