package com.dugu.addressbook.viewmodel;

import com.dugu.addressbook.BR;
import com.dugu.addressbook.R;
import com.dugu.addressbook.viewmodel.item.SearchContactItemViewModel;

import java.util.List;

public class SearchContactViewModel extends BindingItem{

    private List<SearchContactItemViewModel> itemViewModelList;

    public SearchContactViewModel(List<SearchContactItemViewModel> itemViewModelList) {
        this.itemViewModelList = itemViewModelList;
    }

    public List<SearchContactItemViewModel> getItemViewModelList() {
        return itemViewModelList;
    }

    public void setItemViewModelList(List<SearchContactItemViewModel> itemViewModelList) {
        this.itemViewModelList = itemViewModelList;
    }

    @Override
    public int getViewType() {
        return R.layout.frag_search_contact;
    }

    @Override
    public int getViewVariableId() {
        return BR.SearchContactViewModel;
    }
}
