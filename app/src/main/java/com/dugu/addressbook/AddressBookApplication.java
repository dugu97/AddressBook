package com.dugu.addressbook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.dugu.addressbook.db.DBHelper;
import com.dugu.addressbook.db.DaoMaster;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.model.Group;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Arrays;
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
        Database database = dbHelper.getEncryptedWritableDb(DBNAME);
        daoSession = new DaoMaster(database).newSession();

        printDBLog();
        initDefaultData();

        initDBData();


        //适配文件分享URI问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    private void printDBLog() {
        Log.d("123", "Group: " + Arrays.toString(getDaoSession().getGroupDao().getAllColumns()));
        Log.d("123", "Phone: " + Arrays.toString(getDaoSession().getPhoneDao().getAllColumns()));
        Log.d("123", "Email: " + Arrays.toString(getDaoSession().getEmailDao().getAllColumns()));
        Log.d("123", "Contact: " + Arrays.toString(getDaoSession().getContactDao().getAllColumns()));
    }

    private void initDefaultData() {
        getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {

                if (!isFirstRun()) {
                    Log.d("123", "已初始化group表");
                    return;
                }

                //初始化Group表  手机联系人id为3  sim联系人为4
                /*
                    巨坑，数组越界没报错
                 */
                List<Group> groupList = new ArrayList<>();
                for (int i = 0; i < Constants.GROUP_PROJECT.length; i++) {
                    groupList.add(new Group((long) i, Constants.GROUP_PROJECT[i]));
                }
                Log.d("123", groupList.size() + "个初始群组，初始成功");
                getDaoSession().getGroupDao().deleteAll();
                getDaoSession().getGroupDao().insertInTx(groupList);
                getDaoSession().clear();
            }
        });
    }

    private boolean isFirstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun", 0);
        Boolean first_run = sharedPreferences.getBoolean("First", true);
        if (first_run) {
            sharedPreferences.edit().putBoolean("First", false).commit();
            Log.d("123", "第一次运行");
            return true;
        } else {
            Log.d("123", "不是第一次运行");
            return false;
        }
    }

    private void initDBData() {

        //qing清空数据库
//        getDaoSession().getContactDao().deleteAll();
//        getDaoSession().getPhoneDao().deleteAll();
//        getDaoSession().getEmailDao().deleteAll();
//        getDaoSession().getGroupLinkContactDao().deleteAll();

        getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {

//                //初始化Group表  手机联系人id为3  sim联系人为4
//                /*
//                    巨坑，数组越界没报错
//                 */
//                List<Group> groupList = new ArrayList<>();
//                for (int i = 0; i < Constants.GROUP_PROJECT.length; i++) {
//                    groupList.add(new Group((long) i, Constants.GROUP_PROJECT[i]));
//                }
//                Log.d("123", groupList.size() + "个初始群组，初始成功");
//                getDaoSession().getGroupDao().insertInTx(groupList);

//                Contact a;
//                List<Contact> contacts = new ArrayList<>();
//
//                a = new Contact((long) 1,null, "a", "nickname",
//                        null,  null, null,
//                        null,  null, null);
//                contacts.add(a);
//
//                contacts.add(new Contact(null, null, "aa", "nickname",
//                        "organization",  "job",  "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "b", "nickname",
//                        "organization",  "job",    "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "bb", "nickname",
//                        "organization",  "job",   "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "c", "nickname",
//                        "organization",  "job",   "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "cc", "nickname",
//                        "organization",  "job",    "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "ffff", "nickname",
//                        "organization",  "job",   "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "rrrr", "nickname",
//                        "organization",  "job",   "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "hhhhh", "nickname",
//                        "organization",  "job",   "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "tttt", "nickname",
//                        "organization",  "job",   "remark",
//                        "address",   null, null));
//                contacts.add(new Contact(null, null, "ggggg", "nickname",
//                        "organization",  "job",    "remark",
//                        "address",  null, null));
//                getDaoSession().getContactDao().insertInTx(contacts);
//
//                List<Phone> phoneList = new ArrayList<>();
//                phoneList.add(new Phone(null, (long) 1,"13728472475","手机"));
//                phoneList.add(new Phone(null, (long) 1,"15622586568","手机"));
//                phoneList.add(new Phone(null, (long) 1,"15622586577","手机"));
//                phoneList.add(new Phone(null, (long) 1,"15622586544","手机"));
//                getDaoSession().getPhoneDao().insertInTx(phoneList);

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
