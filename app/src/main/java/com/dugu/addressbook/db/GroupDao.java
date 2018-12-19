package com.dugu.addressbook.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.dugu.addressbook.model.Group;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GROUP".
*/
public class GroupDao extends AbstractDao<Group, Long> {

    public static final String TABLENAME = "GROUP";

    /**
     * Properties of entity Group.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Group_id = new Property(0, Long.class, "group_id", true, "_id");
        public final static Property Group_name = new Property(1, String.class, "group_name", false, "GROUP_NAME");
    }

    private DaoSession daoSession;


    public GroupDao(DaoConfig config) {
        super(config);
    }
    
    public GroupDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GROUP\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: group_id
                "\"GROUP_NAME\" TEXT);"); // 1: group_name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GROUP\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Group entity) {
        stmt.clearBindings();
 
        Long group_id = entity.getGroup_id();
        if (group_id != null) {
            stmt.bindLong(1, group_id);
        }
 
        String group_name = entity.getGroup_name();
        if (group_name != null) {
            stmt.bindString(2, group_name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Group entity) {
        stmt.clearBindings();
 
        Long group_id = entity.getGroup_id();
        if (group_id != null) {
            stmt.bindLong(1, group_id);
        }
 
        String group_name = entity.getGroup_name();
        if (group_name != null) {
            stmt.bindString(2, group_name);
        }
    }

    @Override
    protected final void attachEntity(Group entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Group readEntity(Cursor cursor, int offset) {
        Group entity = new Group( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // group_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // group_name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Group entity, int offset) {
        entity.setGroup_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGroup_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Group entity, long rowId) {
        entity.setGroup_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Group entity) {
        if(entity != null) {
            return entity.getGroup_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Group entity) {
        return entity.getGroup_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
