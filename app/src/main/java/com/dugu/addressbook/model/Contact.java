package com.dugu.addressbook.model;

import com.dugu.addressbook.db.ContactDao;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.EmailDao;
import com.dugu.addressbook.db.PhoneDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity
public class Contact {
    @Id(autoincrement = true)
    private Long contact_id;

    private byte[] icon;   //头像
    private String name;
    private String nickname;
    private String organization;
    private String job;
    private String firstPingYin;
    private boolean isSIM;

    // Phone类的contact_id作为外键，与Contact的主键相连。
    @ToMany(referencedJoinProperty = "contact_id")
    private List<Phone> phoneList;

    @ToMany(referencedJoinProperty = "contact_id")
    private List<Email> emailList;

    private String ring;
    private String remark;
    private String address;
    private String birthday;
    private byte[] businessardData;  //二进制名片图片
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2046468181)
    private transient ContactDao myDao;

    @Generated(hash = 672515148)
    public Contact() {
    }
    @Generated(hash = 944752352)
    public Contact(Long contact_id, byte[] icon, String name, String nickname, String organization,
            String job, String firstPingYin, boolean isSIM, String ring, String remark, String address,
            String birthday, byte[] businessardData) {
        this.contact_id = contact_id;
        this.icon = icon;
        this.name = name;
        this.nickname = nickname;
        this.organization = organization;
        this.job = job;
        this.firstPingYin = firstPingYin;
        this.isSIM = isSIM;
        this.ring = ring;
        this.remark = remark;
        this.address = address;
        this.birthday = birthday;
        this.businessardData = businessardData;
    }
    public Long getContact_id() {
        return this.contact_id;
    }
    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }
    public byte[] getIcon() {
        return this.icon;
    }
    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getOrganization() {
        return this.organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getJob() {
        return this.job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getRing() {
        return this.ring;
    }
    public void setRing(String ring) {
        this.ring = ring;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public byte[] getBusinessardData() {
        return this.businessardData;
    }
    public void setBusinessardData(byte[] businessardData) {
        this.businessardData = businessardData;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1807956594)
    public List<Phone> getPhoneList() {
        if (phoneList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhoneDao targetDao = daoSession.getPhoneDao();
            List<Phone> phoneListNew = targetDao
                    ._queryContact_PhoneList(contact_id);
            synchronized (this) {
                if (phoneList == null) {
                    phoneList = phoneListNew;
                }
            }
        }
        return phoneList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1298765061)
    public synchronized void resetPhoneList() {
        phoneList = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 962546259)
    public List<Email> getEmailList() {
        if (emailList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EmailDao targetDao = daoSession.getEmailDao();
            List<Email> emailListNew = targetDao
                    ._queryContact_EmailList(contact_id);
            synchronized (this) {
                if (emailList == null) {
                    emailList = emailListNew;
                }
            }
        }
        return emailList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 864013842)
    public synchronized void resetEmailList() {
        emailList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2088270543)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContactDao() : null;
    }
    public String getFirstPingYin() {
        return this.firstPingYin;
    }
    public void setFirstPingYin(String firstPingYin) {
        this.firstPingYin = firstPingYin;
    }
    public boolean getIsSIM() {
        return this.isSIM;
    }
    public void setIsSIM(boolean isSIM) {
        this.isSIM = isSIM;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
