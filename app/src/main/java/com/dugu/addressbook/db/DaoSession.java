package com.dugu.addressbook.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Email;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.model.GroupLinkContact;
import com.dugu.addressbook.model.Phone;

import com.dugu.addressbook.db.ContactDao;
import com.dugu.addressbook.db.EmailDao;
import com.dugu.addressbook.db.GroupDao;
import com.dugu.addressbook.db.GroupLinkContactDao;
import com.dugu.addressbook.db.PhoneDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig contactDaoConfig;
    private final DaoConfig emailDaoConfig;
    private final DaoConfig groupDaoConfig;
    private final DaoConfig groupLinkContactDaoConfig;
    private final DaoConfig phoneDaoConfig;

    private final ContactDao contactDao;
    private final EmailDao emailDao;
    private final GroupDao groupDao;
    private final GroupLinkContactDao groupLinkContactDao;
    private final PhoneDao phoneDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        contactDaoConfig = daoConfigMap.get(ContactDao.class).clone();
        contactDaoConfig.initIdentityScope(type);

        emailDaoConfig = daoConfigMap.get(EmailDao.class).clone();
        emailDaoConfig.initIdentityScope(type);

        groupDaoConfig = daoConfigMap.get(GroupDao.class).clone();
        groupDaoConfig.initIdentityScope(type);

        groupLinkContactDaoConfig = daoConfigMap.get(GroupLinkContactDao.class).clone();
        groupLinkContactDaoConfig.initIdentityScope(type);

        phoneDaoConfig = daoConfigMap.get(PhoneDao.class).clone();
        phoneDaoConfig.initIdentityScope(type);

        contactDao = new ContactDao(contactDaoConfig, this);
        emailDao = new EmailDao(emailDaoConfig, this);
        groupDao = new GroupDao(groupDaoConfig, this);
        groupLinkContactDao = new GroupLinkContactDao(groupLinkContactDaoConfig, this);
        phoneDao = new PhoneDao(phoneDaoConfig, this);

        registerDao(Contact.class, contactDao);
        registerDao(Email.class, emailDao);
        registerDao(Group.class, groupDao);
        registerDao(GroupLinkContact.class, groupLinkContactDao);
        registerDao(Phone.class, phoneDao);
    }
    
    public void clear() {
        contactDaoConfig.clearIdentityScope();
        emailDaoConfig.clearIdentityScope();
        groupDaoConfig.clearIdentityScope();
        groupLinkContactDaoConfig.clearIdentityScope();
        phoneDaoConfig.clearIdentityScope();
    }

    public ContactDao getContactDao() {
        return contactDao;
    }

    public EmailDao getEmailDao() {
        return emailDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public GroupLinkContactDao getGroupLinkContactDao() {
        return groupLinkContactDao;
    }

    public PhoneDao getPhoneDao() {
        return phoneDao;
    }

}
