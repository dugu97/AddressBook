package com.dugu.addressbook.presenter;

import android.text.SpannableString;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.contract.SearchContactContract;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Email;
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


        //搜索优先级 名字 > 电话 > 电子邮件 > 公司 > 职位
        for (Contact contact : contactList) {

            String name = AppUtil.getContactName(contact);

            if (name.contains(key) || name.equalsIgnoreCase(key)) {
                itemViewModels.add(new SearchContactItemViewModel(contact, AppUtil.matcherSearchTitle(name, key), false, AppUtil.matcherSearchTitle(name, key)));
                continue;
            }

            SpannableString ss = new SpannableString(name);

            boolean isTarget = false;
            List<Phone> phoneList = contact.getPhoneList();
            for (Phone phone : phoneList) {
                String p = phone.getPhone().replace(" ", "");
                if (p.contains(key)) {
                    itemViewModels.add(new SearchContactItemViewModel(contact, ss, true, AppUtil.matcherSearchTitle(p, key)));
                    isTarget = true;
                    break;
                }
            }

            if (isTarget)
                continue;

            isTarget = false;
            List<Email> emailList = contact.getEmailList();
            for (Email email : emailList) {
                String e = email.getEmail().replace(" ", "");
                if (e.contains(key)) {
                    itemViewModels.add(new SearchContactItemViewModel(contact, ss, true, AppUtil.matcherSearchTitle(e, key)));
                    isTarget = true;
                    break;
                }
            }

            if (isTarget)
                continue;

            if (!AppUtil.isNullString(contact.getOrganization())) {
                String organization = contact.getOrganization().replace(" ", "");
                if (organization.contains(key) || organization.equalsIgnoreCase(key)) {
                    itemViewModels.add(new SearchContactItemViewModel(contact, ss, true, AppUtil.matcherSearchTitle(organization, key)));
                    continue;
                }
            }

            if (!AppUtil.isNullString(contact.getJob())) {
                String job = contact.getJob().replace(" ", "");
                if (job.contains(key) || job.equalsIgnoreCase(key)) {
                    itemViewModels.add(new SearchContactItemViewModel(contact, ss, true, AppUtil.matcherSearchTitle(job, key)));
                    continue;
                }
            }

            if (!AppUtil.isNullString(contact.getNickname())) {
                String nickname = contact.getNickname().replace(" ", "");
                if (nickname.contains(key) || nickname.equalsIgnoreCase(key)) {
                    itemViewModels.add(new SearchContactItemViewModel(contact, ss, true, AppUtil.matcherSearchTitle(nickname, key)));
                    continue;
                }
            }

            if (!AppUtil.isNullString(contact.getAddress())) {
                String address = contact.getAddress().replace(" ", "");
                if (address.contains(key) || address.equalsIgnoreCase(key)) {
                    itemViewModels.add(new SearchContactItemViewModel(contact, ss, true, AppUtil.matcherSearchTitle(address, key)));
                    continue;
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
            String name = AppUtil.getContactName(contactList.get(i));
            SpannableString ss = new SpannableString(name);
            itemViewModels.add(new SearchContactItemViewModel(contactList.get(i), ss, false, null));
        }

        searchContactViewModel = new SearchContactViewModel(itemViewModels);

        mUi.showResult();
    }
}
