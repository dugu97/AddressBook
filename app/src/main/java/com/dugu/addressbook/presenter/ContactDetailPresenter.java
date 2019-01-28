package com.dugu.addressbook.presenter;

import android.util.Log;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.ContactDetailContract;
import com.dugu.addressbook.db.ContactDao;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.EmailDao;
import com.dugu.addressbook.db.GroupLinkContactDao;
import com.dugu.addressbook.db.PhoneDao;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Email;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.model.GroupLinkContact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.ContactDetailViewModel;
import com.dugu.addressbook.viewmodel.item.ContactDetailItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactDetailPresenter implements ContactDetailContract.Presenter {

    private final ContactDetailContract.Ui mUi;

    private ContactDetailViewModel contactDetailViewModel;
    private Contact contact;

    public ContactDetailPresenter(ContactDetailContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void createContactDetailViewModel(final Long contact_id) {
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                contact = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().where(ContactDao.Properties.Contact_id.eq(contact_id)).unique();

                contactDetailViewModel = new ContactDetailViewModel(contact_id,
                        contact.getIcon(),
                        contact.getBusinessardData(),
                        contact.getName(),
                        contact.getOrganization(),
                        contact.getJob(),
                        contact.getNickname(),
                        contact.getAddress(),
                        contact.getBirthday(),
                        contact.getGroupList(),
                        contact.getPhoneList(),
                        contact.getEmailList(),
                        contact.getRemark());

                mUi.showResult();
            }
        });
    }

    @Override
    public List<ContactDetailItemViewModel> getAllMessageItems() {
        //应该从ContactDetailViewModel进行适配
        List<ContactDetailItemViewModel> messageItems = new ArrayList<>();
        if (contactDetailViewModel == null)
            return messageItems;

        List<Phone> phoneList = contactDetailViewModel.getPhoneList();
        if (!AppUtil.isNullList(phoneList)) {
            for (Phone phone : phoneList) {
                messageItems.add(new ContactDetailItemViewModel(Constants.SORTKEY_PHONE, phone.getPhone_name(), phone.getPhone()));
            }
            Log.d("123", phoneList.size() + "phone");
        }

        List<Email> emailList = contactDetailViewModel.getEmailList();
        if (!AppUtil.isNullList(emailList)) {
            for (Email email : emailList) {
                messageItems.add(new ContactDetailItemViewModel(Constants.SORTKEY_EMAIL, email.getEmail_name(), email.getEmail()));
            }
        }

        String groups = "";
        boolean isContainOther = false;
        List<Group> groupList = contactDetailViewModel.getGroupList();
        if (!AppUtil.isNullList(groupList)) {
            for (int i = 0; i < groupList.size(); i++) {
                if (groupList.get(i).getGroup_id() > Constants.GROUP_BLACK) {
                    if (groups.equals(""))
                        groups = groupList.get(i).getGroup_name();
                    else
                        groups = groups + ", " + groupList.get(i).getGroup_name();

                    isContainOther = true;
                }
            }
            if (isContainOther)
                messageItems.add(new ContactDetailItemViewModel(Constants.SORTKEY_GROUP, "群组", groups));
        }

        if (contactDetailViewModel.getBusinessardData() != null && contactDetailViewModel.getBusinessardData().length > 0)
            messageItems.add(new ContactDetailItemViewModel(Constants.SORTKEY_BUSINESS_CARD, null, "查看名片"));
        if (!AppUtil.isNullString(contactDetailViewModel.getNickname()))
            messageItems.add(new ContactDetailItemViewModel(Constants.SORTKEY_NICKNAME, "昵称", contact.getNickname()));
        if (!AppUtil.isNullString(contactDetailViewModel.getAddress()))
            messageItems.add(new ContactDetailItemViewModel(Constants.SORTKEY_ADDRESS, "地址", contact.getAddress()));
        if (!AppUtil.isNullString(contactDetailViewModel.getBirthday()))
            messageItems.add(new ContactDetailItemViewModel(Constants.SORTKEY_BIRTHDAY, "生日", contact.getBirthday()));
        if (!AppUtil.isNullString(contactDetailViewModel.getRemark()))
            messageItems.add(new ContactDetailItemViewModel(Constants.SORTKEY_REMARK, "备注", contact.getRemark()));

        return messageItems;
    }

    @Override
    public void deleteContact(Long contact_id) {
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

        daoSession.clear();
    }

    @Override
    public ContactDetailViewModel getContactDetailViewModel() {
        return contactDetailViewModel;
    }
}
