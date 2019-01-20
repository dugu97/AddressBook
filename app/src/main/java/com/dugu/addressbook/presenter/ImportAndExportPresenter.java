package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.ImportAndExportContract;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.model.Email;
import com.dugu.addressbook.model.GroupLinkContact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.viewmodel.ImportAndExportViewModel;

import java.util.ArrayList;
import java.util.List;

public class ImportAndExportPresenter implements ImportAndExportContract.Presenter {

    private final ImportAndExportContract.Ui mUi;

    private ImportAndExportViewModel importAndExportViewModel;

    public ImportAndExportPresenter(ImportAndExportContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public ImportAndExportViewModel getImportAndExportViewModel() {
        return importAndExportViewModel;
    }

    @Override
    public void start() {
        List<Contact> contactList = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().list();
        importAndExportViewModel = new ImportAndExportViewModel(contactList);
        mUi.showResult();
    }

    @Override
    public void importContact(List<ContactWithPhoneAndEmail> models) {

        DaoSession daoSession = AddressBookApplication.getDaoSession();

        for (ContactWithPhoneAndEmail model : models) {

            List<Phone> phoneList = new ArrayList<>();
            List<Email> emailList = new ArrayList<>();

            Contact contact = new Contact(null,
                    model.getIcon(),
                    model.getName(),
                    model.getNickname(),
                    model.getOrganization(),
                    model.getJob(),
                    model.getRemark(),
                    model.getAddress(),
                    model.getBirthday(),
                    null);

            Long contact_id = daoSession.getContactDao().insert(contact);

            if (model.getPhoneList() != null) {
                for (int i = 0; i < model.getPhoneList().size(); i++) {
                    phoneList.add(new Phone(null, contact_id, model.getPhoneList().get(i), "手机"));
                }
            }

            if (model.getEmailList() != null){
                for (int i = 0; i < model.getEmailList().size(); i++) {
                    emailList.add(new Email(null, contact_id, model.getEmailList().get(i), "私人"));
                }
            }

            //插入Phone和Email
            AddressBookApplication.getDaoSession().getPhoneDao().insertInTx(phoneList);
            AddressBookApplication.getDaoSession().getEmailDao().insertInTx(emailList);

            //插入手机联系人群组(基本群组)
            AddressBookApplication.getDaoSession().getGroupLinkContactDao().insert(new GroupLinkContact(null, contact_id, (long) Constants.GROUP_PHONE));
        }

        daoSession.clear();

    }
}
