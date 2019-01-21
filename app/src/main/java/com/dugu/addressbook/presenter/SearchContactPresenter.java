package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.SearchContactContract;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.util.CommonUtil;
import com.dugu.addressbook.viewmodel.SearchContactViewModel;
import com.dugu.addressbook.viewmodel.item.SearchContactItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchContactPresenter implements SearchContactContract.Presenter {


    private final SearchContactContract.Ui mUi;

    private SearchContactViewModel searchContactViewModel;

    private List<Contact> contactList;


    public SearchContactPresenter(SearchContactContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public void searchContact(String key) {

        DaoSession daoSession = AddressBookApplication.getDaoSession();

        List<SearchContactItemViewModel> itemViewModels = new ArrayList<>();

        CommonUtil.sortContactlData(contactList);

        for (Contact contact : contactList) {

            if (!AppUtil.isNullString(contact.getName())) {
                String name = contact.getName().replace(" ", "");
                if (name.contains(key) || name.equalsIgnoreCase(key)) {
                    itemViewModels.add(new SearchContactItemViewModel(contact));
                    continue;
                }
            }

            List<Phone> phoneList = contact.getPhoneList();

            for (Phone phone : phoneList) {
                if (phone.getPhone().contains(key)){
                    itemViewModels.add(new SearchContactItemViewModel(contact));
                    break;
                }
            }
        }

        searchContactViewModel = new SearchContactViewModel(itemViewModels);

        mUi.showResult();
    }

    @Override
    public SearchContactViewModel getSearchContactViewModel() {
        return searchContactViewModel;
    }

    @Override
    public void start() {
        DaoSession daoSession = AddressBookApplication.getDaoSession();

        List<SearchContactItemViewModel> itemViewModels = new ArrayList<>();

        contactList = daoSession.getContactDao().queryBuilder().list();

        for (int i = 0; i < contactList.size(); i++) {
            itemViewModels.add(new SearchContactItemViewModel(contactList.get(i)));
        }

        searchContactViewModel = new SearchContactViewModel(itemViewModels);

        mUi.showResult();
    }
}
