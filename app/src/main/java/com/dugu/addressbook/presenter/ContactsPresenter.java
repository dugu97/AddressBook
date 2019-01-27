package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.ContactsContract;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.EmailDao;
import com.dugu.addressbook.db.GroupLinkContactDao;
import com.dugu.addressbook.db.PhoneDao;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Email;
import com.dugu.addressbook.model.GroupLinkContact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.viewmodel.ContactsViewModel;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsPresenter implements ContactsContract.Presenter {

    private final ContactsContract.Ui mUi;
    private ContactsViewModel contactsViewModel;

    public ContactsPresenter(ContactsContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public void start() {
        List<Contact> list = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().list();

        contactsViewModel = new ContactsViewModel(formatData(list));

        //数据加载完成 显示UI
        mUi.showResult();
    }


    private List<ContactItemViewModel> formatData(List<Contact> list) {

        List<ContactItemViewModel> result = new ArrayList<>();

        ContactItemViewModel item;

        if (list == null)
            return result;

        for (Contact contact :
                list) {

            item = new ContactItemViewModel(contact);

            result.add(item);
        }

        return result;
    }

    @Override
    public void addContactInBlack(final Long contact_id) {
        DaoSession daoSession = AddressBookApplication.getDaoSession();
        daoSession.getGroupLinkContactDao().insert(new GroupLinkContact(null, contact_id, (long) Constants.GROUP_BLACK));
        daoSession.clear();
    }

    @Override
    public void deleteContact(final Long contact_id) {
        DaoSession daoSession = AddressBookApplication.getDaoSession();

        daoSession.getContactDao().deleteByKey(contact_id);

        List<GroupLinkContact> groupLinkContact = daoSession.getGroupLinkContactDao().queryBuilder()
                .where(GroupLinkContactDao.Properties.Contact_id.eq(contact_id)).list();
        daoSession.getGroupLinkContactDao().deleteInTx(groupLinkContact);

        List<Phone> phoneList = daoSession.getPhoneDao().queryBuilder()
                .where(PhoneDao.Properties.Contact_id.eq(contact_id)).list();
        daoSession.getPhoneDao().deleteInTx(phoneList);

        List<Email> emailList = daoSession.getEmailDao().queryBuilder()
                .where(EmailDao.Properties.Contact_id.eq(contact_id)).list();
        daoSession.getEmailDao().deleteInTx(emailList);

        daoSession.getContactDao().detachAll();

        //数据加载完成 显示UI
        mUi.showResult();
    }

    @Override
    public ContactsViewModel getContactsViewModel() {
        return contactsViewModel;
    }
}
