package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.R;

public class ShowImageViewModel extends BindingItem {

    private byte[] image;

    public ShowImageViewModel(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_show_image;
    }

    @Override
    public int getViewVariableId() {
        return 0;
    }
}
