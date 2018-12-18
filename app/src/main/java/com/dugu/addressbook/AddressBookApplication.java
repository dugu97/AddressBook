package com.dugu.addressbook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.dugu.addressbook.db.DBHelper;
import com.dugu.addressbook.db.DaoMaster;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.Phone;

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

                Contact a = new Contact((long) 1, null, "a", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null);
                contacts.add(a);

                contacts.add(new Contact(null, null, "aa", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "b", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "bb", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "c", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "cc", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "ffff", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "rrrr", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "hhhhh", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "tttt", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                contacts.add(new Contact(null, null, "ggggg", "nickname",
                        "organization",  "job", "ring", "remark",
                        "address", "postCode",  null, null));
                getDaoSession().getContactDao().insertInTx(contacts);

                List<Phone> phoneList = new ArrayList<>();
                phoneList.add(new Phone(null, (long) 1,"13728472475","手机"));
                phoneList.add(new Phone(null, (long) 1,"15622586568","手机"));
                getDaoSession().getPhoneDao().insertInTx(phoneList);

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
