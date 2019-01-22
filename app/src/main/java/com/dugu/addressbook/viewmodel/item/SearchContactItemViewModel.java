package com.dugu.addressbook.viewmodel.item;

import android.text.SpannableString;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.viewmodel.BindingItem;

public class SearchContactItemViewModel extends BindingItem {

    private Contact contact;

    private SpannableString targetName;

    private boolean isTargetOtherMeg;
    private SpannableString targetContent;

    public SearchContactItemViewModel(Contact contact,  SpannableString targetName, boolean isTargetOtherMeg, SpannableString targetContent) {
        this.contact = contact;
        this.targetName = targetName;


        this.isTargetOtherMeg = isTargetOtherMeg;

        if (isTargetOtherMeg)
            this.targetContent = targetContent;
    }

    public SearchContactItemViewModel(Contact contact, boolean isTargetName, SpannableString targetName, boolean isTargetOtherMeg, SpannableString targetContent) {
        this.contact = contact;

        this.targetName = targetName;

        this.isTargetOtherMeg = isTargetOtherMeg;
        if (isTargetOtherMeg)
            this.targetContent = targetContent;
    }

    public boolean needHideSearchText() {
        if (isTargetOtherMeg)
            return false;
        return true;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public SpannableString getTargetName() {
        return targetName;
    }

    public void setTargetName(SpannableString targetName) {
        this.targetName = targetName;
    }

    public SpannableString getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(SpannableString targetContent) {
        this.targetContent = targetContent;
    }

    public boolean isTargetOtherMeg() {
        return isTargetOtherMeg;
    }

    public void setTargetOtherMeg(boolean targetOtherMeg) {
        isTargetOtherMeg = targetOtherMeg;
    }

    @Override
    public int getViewType() {
        return R.layout.item_search_contact;
    }

    @Override
    public int getViewVariableId() {
        return BR.SearchContactItemViewModel;
    }
}
