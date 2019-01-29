package com.dugu.addressbook.presenter;

import android.util.Log;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.NewOrEditContactContract;
import com.dugu.addressbook.db.ContactDao;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.EmailDao;
import com.dugu.addressbook.db.GroupLinkContactDao;
import com.dugu.addressbook.db.PhoneDao;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.model.Email;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.model.GroupLinkContact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.util.VCardUtil;
import com.dugu.addressbook.viewmodel.NewOrEditContactViewModel;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewOrEditContactPresenter implements NewOrEditContactContract.Presenter {

    private final NewOrEditContactContract.Ui mUi;

    private NewOrEditContactViewModel newOrEditContactViewModel;


    public NewOrEditContactPresenter(NewOrEditContactContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public NewOrEditContactViewModel getNewOrEditContactViewModel() {
        return newOrEditContactViewModel;
    }

    @Override
    public void createViewModel(int mode, Long contact_id) {
        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT) {
            newOrEditContactViewModel = new NewOrEditContactViewModel(mode,
                    null,
                    null,
                    null,
                    null,
                    null,
                    new ArrayList<ContactInputItemViewModel>(),
                    new ArrayList<Group>());
        }

        if (mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT) {
            Contact contact = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().where(ContactDao.Properties.Contact_id.eq(contact_id)).unique();

            newOrEditContactViewModel = new NewOrEditContactViewModel(mode,
                    contact_id,
                    contact.getIcon(),
                    contact.getName(),
                    contact.getOrganization(),
                    contact.getJob(),
                    getContactInputList(contact),
                    contact.getGroupList());

            if (!AppUtil.isNullString(contact.getBirthday()))
                newOrEditContactViewModel.setBirthday(contact.getBirthday());
        }

        mUi.showResult();
    }


    @Override
    public void createViewModelWithQrCode(int mode, String contactWithPhoneAndEmail) {
        ContactWithPhoneAndEmail contact = VCardUtil.parseVCard(contactWithPhoneAndEmail);

        List<ContactInputItemViewModel> inputList = new ArrayList<>();

        //特别注意该字段
        int serialNumber = 1;

        //新建手机联系人基本输入信息 (通过二维码)
        List<String> phoneList = contact.getPhoneList();
        if (phoneList != null) {
            for (int i = 0; i < phoneList.size(); i++) {
                inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_PHONE, serialNumber, "手机", phoneList.get(i)));
                serialNumber ++;
            }
        } else {
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_PHONE, serialNumber, "手机", ""));
        }

        List<String> emailList = contact.getEmailList();
        if (emailList != null) {
            for (int i = 0; i < phoneList.size(); i++) {
                inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_EMAIL,  serialNumber, "私人", emailList.get(i)));
                serialNumber ++;
            }
        } else {
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_EMAIL, serialNumber, "私人", ""));
        }

        inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_REMARK, 3, "备注", ""));
        inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_NICKNAME, 4, "昵称", ""));
        inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_ADDRESS, 5, "地址", ""));

        //新建手机联系人初始群组
        List<Group> groupList = new ArrayList<>();
        groupList.add(new Group((long) Constants.GROUP_PHONE, Constants.GROUP_PROJECT[Constants.GROUP_PHONE]));


        newOrEditContactViewModel = new NewOrEditContactViewModel(mode,
                null,
                null,
                contact.getName(),
                null,
                null,
                inputList,
                groupList);

        mUi.showResult();
    }

    @Override
    public void createOrUpdateContact(final NewOrEditContactViewModel viewModel) {

        DaoSession daoSession = AddressBookApplication.getDaoSession();
        List<ContactInputItemViewModel> inputList = viewModel.getInputList();

        String nickName = null;
        String remark = null;
        String address = null;
        List<Phone> phoneList = new ArrayList<>();
        List<Email> emailList = new ArrayList<>();
        ContactInputItemViewModel data;

        for (int i = 0; i < inputList.size(); i++) {
            data = inputList.get(i);
            if (data.getSortKey() == Constants.SORTKEY_NICKNAME) {
                if (!AppUtil.isNullString(data.getContent()))
                    nickName = data.getContent();
            } else if (data.getSortKey() == Constants.SORTKEY_REMARK) {
                if (!AppUtil.isNullString(data.getContent()))
                    remark = data.getContent();
            } else if (data.getSortKey() == Constants.SORTKEY_ADDRESS) {
                if (!AppUtil.isNullString(data.getContent()))
                    address = data.getContent();
            }
        }

        Contact contact = new Contact(viewModel.getContact_id(),
                viewModel.getIcon(),
                viewModel.getName(),
                nickName,
                viewModel.getOrganization(),
                viewModel.getJob(),
                remark,
                address,
                viewModel.getBirthday(),
                null);


        Long contactId = viewModel.getContact_id();

        // 插入联系人
        if (viewModel.getMode() == Constants.CONTACT_MODE_NEW_PHONE_CONTACT || viewModel.getMode() == Constants.CONTACT_MODE_NEW_PHONE_CONTACT_WITH_QR_CODE) {
            contactId = daoSession.getContactDao().insert(contact);
            contact.setContact_id(contactId);
        } else if (viewModel.getMode() == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT) {
            daoSession.getContactDao().update(contact);

            daoSession.getPhoneDao().deleteInTx(
                    daoSession.getPhoneDao().queryBuilder()
                            .where(PhoneDao.Properties.Contact_id.eq(contactId)).list()
            );

            daoSession.getEmailDao().deleteInTx(
                    daoSession.getEmailDao().queryBuilder()
                            .where(EmailDao.Properties.Contact_id.eq(contactId)).list()
            );

            daoSession.getGroupLinkContactDao().deleteInTx(
                    daoSession.getGroupLinkContactDao().queryBuilder()
                            .where(GroupLinkContactDao.Properties.Contact_id.eq(contactId)).list()
            );
        }


        for (int i = 0; i < inputList.size(); i++) {
            data = inputList.get(i);
            if (data.getSortKey() == Constants.SORTKEY_PHONE) {
                if (!AppUtil.isNullString(data.getContent()))
                    phoneList.add(new Phone(null, contactId, data.getContent(), data.getTitle()));
            } else if (data.getSortKey() == Constants.SORTKEY_EMAIL) {
                if (!AppUtil.isNullString(data.getContent()))
                    emailList.add(new Email(null, contactId, data.getContent(), data.getTitle()));
            }
        }

        //插入Phone和Email
        AddressBookApplication.getDaoSession().getPhoneDao().insertInTx(phoneList);
        AddressBookApplication.getDaoSession().getEmailDao().insertInTx(emailList);

        //插入群组
        for (int i = 0; i < viewModel.getGroupList().size(); i++) {
            AddressBookApplication.getDaoSession().getGroupLinkContactDao()
                    .insert(new GroupLinkContact(
                            null,
                            contactId,
                            viewModel.getGroupList().get(i).getGroup_id()));
        }

        daoSession.clear();
    }

    private List<ContactInputItemViewModel> getContactInputList(Contact contact) {

        //特别注意   无数据时也要初始输入框
        List<ContactInputItemViewModel> inputList = new ArrayList<>();

        List<Phone> phoneList;
        List<Email> emailList;

        phoneList = contact.getPhoneList();
        emailList = contact.getEmailList();

        if (phoneList.size() == 0)
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_PHONE, inputList.size(), "手机", ""));
        for (int i = 0; i < phoneList.size(); i++) {
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_PHONE, inputList.size(), phoneList.get(i).getPhone_name(), phoneList.get(i).getPhone()));
        }

        if (emailList.size() == 0)
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_EMAIL, inputList.size(), "私人", ""));
        for (int i = 0; i < emailList.size(); i++) {
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_EMAIL, inputList.size(), emailList.get(i).getEmail_name(), emailList.get(i).getEmail()));
        }

        if (!AppUtil.isNullString(contact.getNickname()))
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_NICKNAME, inputList.size(), "昵称", contact.getNickname()));
        else
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_NICKNAME, inputList.size(), "昵称", ""));


        if (!AppUtil.isNullString(contact.getAddress()))
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_ADDRESS, inputList.size(), "地址", contact.getAddress()));
        else
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_ADDRESS, inputList.size(), "地址", ""));


        if (!AppUtil.isNullString(contact.getRemark()))
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_REMARK, inputList.size(), "备注", contact.getRemark()));
        else
            inputList.add(new ContactInputItemViewModel(Constants.SORTKEY_REMARK, inputList.size(), "备注", ""));


        Log.d("123", inputList.size() + "");

        return inputList;
    }
}
