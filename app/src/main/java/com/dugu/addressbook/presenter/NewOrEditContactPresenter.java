package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.NewOrEditContactContract;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Email;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.model.GroupLinkContact;
import com.dugu.addressbook.model.Phone;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.NewOrEditContactViewModel;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewOrEditContactPresenter implements NewOrEditContactContract.Presenter {

    private final NewOrEditContactContract.Ui mUi;

    private NewOrEditContactViewModel newOrEditContactViewModel;

    private Contact contact;

    public NewOrEditContactPresenter(NewOrEditContactContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public void start() {
        // 异步加载单个联系人（Edit的情况）
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public NewOrEditContactViewModel getNewOrEditContactViewModel() {
        return newOrEditContactViewModel;
    }

    @Override
    public void createViewModel(int mode, Long contact_id) {
        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT) {
            newOrEditContactViewModel = new NewOrEditContactViewModel(mode, null, null, null, null, null, new ArrayList<ContactInputItemViewModel>(), new ArrayList<Group>());
        }

        if (mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT) {

        }
    }

    @Override
    public void createContact(final NewOrEditContactViewModel viewModel) {
        AddressBookApplication.getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {

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

                // 插入联系人
                Long contactId = AddressBookApplication.getDaoSession().getContactDao()
                        .insert(new Contact(viewModel.getContact_id(),
                                viewModel.getIcon(),
                                viewModel.getName(),
                                nickName,
                                viewModel.getOrganization(),
                                viewModel.getJob(),
                                remark,
                                address,
                                viewModel.getBirthday(),
                                null));

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
            }
        });
    }
}
