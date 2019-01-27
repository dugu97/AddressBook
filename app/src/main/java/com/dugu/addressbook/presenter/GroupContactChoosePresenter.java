package com.dugu.addressbook.presenter;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.contract.GroupContactChooseContract;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.GroupDao;
import com.dugu.addressbook.db.GroupLinkContactDao;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.model.GroupLinkContact;
import com.dugu.addressbook.viewmodel.GroupContactChooseViewModel;
import com.dugu.addressbook.viewmodel.item.GroupContactChooseItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupContactChoosePresenter implements GroupContactChooseContract.Presenter {

    private final GroupContactChooseContract.Ui mUi;

    private GroupContactChooseViewModel contactChooseViewModel;

    public GroupContactChoosePresenter(GroupContactChooseContract.Ui mUi) {
        this.mUi = mUi;
        mUi.setPresenter(this);
    }

    @Override
    public GroupContactChooseViewModel getContactChooseViewModel() {
        return contactChooseViewModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void createContactChooseViewModel(Long group_id, int mode) {
        DaoSession daoSession = AddressBookApplication.getDaoSession();
        Group group = daoSession.getGroupDao().queryBuilder().where(GroupDao.Properties.Group_id.eq(group_id)).unique();
        List<Contact> allContactList = daoSession.getContactDao().queryBuilder().list();
        List<Contact> contactInGroup = group.getContactList();

        List<GroupContactChooseItemViewModel> itemViewModels = new ArrayList<>();

        if (mode == Constants.CONTACT_MODE_WITHOUT_GROUP) {
            boolean isInGroup = false;
            for (int i = 0; i < allContactList.size(); i++) {
                for (int j = 0; j < contactInGroup.size(); j++) {
                    if (contactInGroup.get(j).getContact_id().equals(allContactList.get(i).getContact_id())) {
                        isInGroup = true;
                        break;
                    }
                }
                if (!isInGroup)
                    itemViewModels.add(new GroupContactChooseItemViewModel(allContactList.get(i), false));
                isInGroup = false;
            }
        } else if (mode == Constants.CONTACT_MODE_WITHIN_GROUP) {
            for (Contact contact : contactInGroup) {
                itemViewModels.add(new GroupContactChooseItemViewModel(contact, false));
            }
        }

        contactChooseViewModel = new GroupContactChooseViewModel(group, itemViewModels);
        mUi.showResult();
    }

    @Override
    public void insertOrDeleteContactsToGroup(List<GroupContactChooseItemViewModel> viewModels, int mode) {
        DaoSession daoSession = AddressBookApplication.getDaoSession();
        if (mode == Constants.CONTACT_MODE_WITHOUT_GROUP) {
            for (GroupContactChooseItemViewModel model : viewModels) {
                if (model.isChecked())
                    daoSession.getGroupLinkContactDao().insert(new GroupLinkContact(null, model.getContact().getContact_id(), contactChooseViewModel.getGroup().getGroup_id()));
            }
        } else if (mode == Constants.CONTACT_MODE_WITHIN_GROUP) {
            for (GroupContactChooseItemViewModel model : viewModels) {
                if (model.isChecked()) {
                    List<GroupLinkContact> groupLinkContactList = daoSession.getGroupLinkContactDao().queryBuilder()
                            .where(GroupLinkContactDao.Properties.Contact_id.eq(model.getContact().getContact_id()),
                                    GroupLinkContactDao.Properties.Group_id.eq(contactChooseViewModel.getGroup().getGroup_id())).list();
                    daoSession.getGroupLinkContactDao().deleteInTx(groupLinkContactList);
                }
            }
        }
        daoSession.clear();
    }
}
