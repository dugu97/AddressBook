package com.dugu.addressbook.viewmodel.item;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.BindingItem;

public class PhoneImportContactChooseItemViewModel extends BindingItem {

    private ContactWithPhoneAndEmail contactWithPhoneAndEmail;
    private boolean isChecked;

    public PhoneImportContactChooseItemViewModel(ContactWithPhoneAndEmail contactWithPhoneAndEmail, boolean isChecked) {
        this.contactWithPhoneAndEmail = contactWithPhoneAndEmail;
        this.isChecked = isChecked;
    }

    public String getNameOrPhone() {
        if (!AppUtil.isNullString(contactWithPhoneAndEmail.getName()))
            return contactWithPhoneAndEmail.getName();
        else if (!contactWithPhoneAndEmail.getPhoneList().isEmpty())
            return contactWithPhoneAndEmail.getPhoneList().get(0);
        else
            return "(无姓名)";
    }

    public ContactWithPhoneAndEmail getContactWithPhoneAndEmail() {
        return contactWithPhoneAndEmail;
    }

    public void setContactWithPhoneAndEmail(ContactWithPhoneAndEmail contactWithPhoneAndEmail) {
        this.contactWithPhoneAndEmail = contactWithPhoneAndEmail;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int getViewType() {
        return R.layout.item_phone_import_contact_choose;
    }

    @Override
    public int getViewVariableId() {
        return BR.PhoneImportContactChooseItemViewModel;
    }
}
