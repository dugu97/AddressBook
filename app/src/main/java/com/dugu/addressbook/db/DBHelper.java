package com.dugu.addressbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends DaoMaster.OpenHelper {

    public DBHelper(Context context, String name) {
        super(context, name);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

    }
}
