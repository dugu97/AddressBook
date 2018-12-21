package com.dugu.addressbook.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.dugu.addressbook.model.GroupLinkContact;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GROUP_LINK_CONTACT".
*/
public class GroupLinkContactDao extends AbstractDao<GroupLinkContact, Long> {

    public static final String TABLENAME = "GROUP_LINK_CONTACT";

    /**
     * Properties of entity GroupLinkContact.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Contact_id = new Property(1, Long.class, "contact_id", false, "CONTACT_ID");
        public final static Property Group_id = new Property(2, Long.class, "group_id", false, "GROUP_ID");
    }


    public GroupLinkContactDao(DaoConfig config) {
        super(config);
    }
    
    public GroupLinkContactDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GROUP_LINK_CONTACT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CONTACT_ID\" INTEGER," + // 1: contact_id
                "\"GROUP_ID\" INTEGER);"); // 2: group_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GROUP_LINK_CONTACT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GroupLinkContact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long contact_id = entity.getContact_id();
        if (contact_id != null) {
            stmt.bindLong(2, contact_id);
        }
 
        Long group_id = entity.getGroup_id();
        if (group_id != null) {
            stmt.bindLong(3, group_id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GroupLinkContact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long contact_id = entity.getContact_id();
        if (contact_id != null) {
            stmt.bindLong(2, contact_id);
        }
 
        Long group_id = entity.getGroup_id();
        if (group_id != null) {
            stmt.bindLong(3, group_id);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GroupLinkContact readEntity(Cursor cursor, int offset) {
        GroupLinkContact entity = new GroupLinkContact( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // contact_id
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // group_id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GroupLinkContact entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContact_id(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setGroup_id(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GroupLinkContact entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GroupLinkContact entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GroupLinkContact entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
