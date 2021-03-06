package com.dugu.addressbook.db;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.dugu.addressbook.model.GroupLinkContact;

import com.dugu.addressbook.model.Contact;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONTACT".
*/
public class ContactDao extends AbstractDao<Contact, Long> {

    public static final String TABLENAME = "CONTACT";

    /**
     * Properties of entity Contact.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Contact_id = new Property(0, Long.class, "contact_id", true, "_id");
        public final static Property Icon = new Property(1, byte[].class, "icon", false, "ICON");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Nickname = new Property(3, String.class, "nickname", false, "NICKNAME");
        public final static Property Organization = new Property(4, String.class, "organization", false, "ORGANIZATION");
        public final static Property Job = new Property(5, String.class, "job", false, "JOB");
        public final static Property Remark = new Property(6, String.class, "remark", false, "REMARK");
        public final static Property Address = new Property(7, String.class, "address", false, "ADDRESS");
        public final static Property Birthday = new Property(8, String.class, "birthday", false, "BIRTHDAY");
        public final static Property BusinessardData = new Property(9, byte[].class, "businessardData", false, "BUSINESSARD_DATA");
    }

    private DaoSession daoSession;

    private Query<Contact> group_ContactListQuery;

    public ContactDao(DaoConfig config) {
        super(config);
    }
    
    public ContactDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONTACT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: contact_id
                "\"ICON\" BLOB," + // 1: icon
                "\"NAME\" TEXT," + // 2: name
                "\"NICKNAME\" TEXT," + // 3: nickname
                "\"ORGANIZATION\" TEXT," + // 4: organization
                "\"JOB\" TEXT," + // 5: job
                "\"REMARK\" TEXT," + // 6: remark
                "\"ADDRESS\" TEXT," + // 7: address
                "\"BIRTHDAY\" TEXT," + // 8: birthday
                "\"BUSINESSARD_DATA\" BLOB);"); // 9: businessardData
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONTACT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Contact entity) {
        stmt.clearBindings();
 
        Long contact_id = entity.getContact_id();
        if (contact_id != null) {
            stmt.bindLong(1, contact_id);
        }
 
        byte[] icon = entity.getIcon();
        if (icon != null) {
            stmt.bindBlob(2, icon);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String organization = entity.getOrganization();
        if (organization != null) {
            stmt.bindString(5, organization);
        }
 
        String job = entity.getJob();
        if (job != null) {
            stmt.bindString(6, job);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(7, remark);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(9, birthday);
        }
 
        byte[] businessardData = entity.getBusinessardData();
        if (businessardData != null) {
            stmt.bindBlob(10, businessardData);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Contact entity) {
        stmt.clearBindings();
 
        Long contact_id = entity.getContact_id();
        if (contact_id != null) {
            stmt.bindLong(1, contact_id);
        }
 
        byte[] icon = entity.getIcon();
        if (icon != null) {
            stmt.bindBlob(2, icon);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String organization = entity.getOrganization();
        if (organization != null) {
            stmt.bindString(5, organization);
        }
 
        String job = entity.getJob();
        if (job != null) {
            stmt.bindString(6, job);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(7, remark);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(9, birthday);
        }
 
        byte[] businessardData = entity.getBusinessardData();
        if (businessardData != null) {
            stmt.bindBlob(10, businessardData);
        }
    }

    @Override
    protected final void attachEntity(Contact entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Contact readEntity(Cursor cursor, int offset) {
        Contact entity = new Contact( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // contact_id
            cursor.isNull(offset + 1) ? null : cursor.getBlob(offset + 1), // icon
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // organization
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // job
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // remark
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // address
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // birthday
            cursor.isNull(offset + 9) ? null : cursor.getBlob(offset + 9) // businessardData
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Contact entity, int offset) {
        entity.setContact_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIcon(cursor.isNull(offset + 1) ? null : cursor.getBlob(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNickname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setOrganization(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setJob(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRemark(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAddress(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setBirthday(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setBusinessardData(cursor.isNull(offset + 9) ? null : cursor.getBlob(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Contact entity, long rowId) {
        entity.setContact_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Contact entity) {
        if(entity != null) {
            return entity.getContact_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Contact entity) {
        return entity.getContact_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "contactList" to-many relationship of Group. */
    public List<Contact> _queryGroup_ContactList(Long group_id) {
        synchronized (this) {
            if (group_ContactListQuery == null) {
                QueryBuilder<Contact> queryBuilder = queryBuilder();
                queryBuilder.join(GroupLinkContact.class, GroupLinkContactDao.Properties.Contact_id)
                    .where(GroupLinkContactDao.Properties.Group_id.eq(group_id));
                group_ContactListQuery = queryBuilder.build();
            }
        }
        Query<Contact> query = group_ContactListQuery.forCurrentThread();
        query.setParameter(0, group_id);
        return query.list();
    }

}
