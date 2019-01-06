package com.dugu.addressbook.adapter;

import android.text.Editable;
import android.text.TextWatcher;

import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

public class EditContentWatcher implements TextWatcher {

    private ContactInputItemViewModel itemViewModel;

    public EditContentWatcher(ContactInputItemViewModel itemViewModel) {
        this.itemViewModel = itemViewModel;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        itemViewModel.setContent(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
