package com.dugu.addressbook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.dugu.addressbook.db.DBHelper;
import com.dugu.addressbook.db.DaoMaster;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.model.Contact;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

public class AddressBookApplication extends Application {

    private static Context appContext;
    private static Activity mCurActivity;

    /**
     * greendao 操作是数据库
     */
    private static DaoSession daoSession;

    /*本地数据库名字*/
    private static final String DBNAME = "addressbook.db";

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        //数据库
        DBHelper dbHelper = new DBHelper(this, DBNAME);
        Database database=dbHelper.getEncryptedWritableDb(DBNAME);
        daoSession=new DaoMaster(database).newSession();

        initDBData();
    }

    private void initDBData(){
        getDaoSession().getContactDao().deleteAll();
        getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<Contact> contacts = new ArrayList<>();
                contacts.add(new Contact(null, null, "a", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "b", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "c", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                getDaoSession().getContactDao().insertInTx(contacts);
            }
        });
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static Activity getmCurActivity() {
        return mCurActivity;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static void setmCurActivity(Activity mCurActivity) {
        AddressBookApplication.mCurActivity = mCurActivity;
    }

}
