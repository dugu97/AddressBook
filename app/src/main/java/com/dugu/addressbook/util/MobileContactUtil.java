package com.dugu.addressbook.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.dugu.addressbook.model.ContactWithPhoneAndEmail;

import java.util.ArrayList;
import java.util.List;

public class MobileContactUtil {

    public static List<ContactWithPhoneAndEmail> getAllContactWithSim(Context context) throws Throwable {
        //获取联系人信息的Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
//        Uri uri = Uri.parse("content://icc/adn");
        //获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        //查询数据，返回Cursor
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        List<ContactWithPhoneAndEmail> contactList = new ArrayList<>();
//        int count = cursor.getColumnCount();
//        while (cursor.moveToNext()){
//            Log.d("123", cursor.getColumnCount() + " sum");
//            for (int i = 0; i < count; i++) {
//                Log.d("123", cursor.getColumnName(i) + ":" + cursor.getString(i));
//            }
//        }

        Log.d("123", cursor.getCount() + "");
        while (cursor.moveToNext()) {
            ContactWithPhoneAndEmail contact = new ContactWithPhoneAndEmail();

            List<String> phoneList = new ArrayList<>();
            List<String> emailList = new ArrayList<>();

            //获取联系人的ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            //获取联系人的姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contact.setName(name);

            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));//联系人ID

            //查询电话类型的数据操作
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                //添加Phone的信息
                phoneNumber.replace(" ", "");
                phoneList.add(phoneNumber);
            }
            contact.setPhoneList(phoneList);
            phones.close();

            //查询Email类型的数据操作
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null, null);
            while (emails.moveToNext()) {
                String emailAddress = emails.getString(emails.getColumnIndex(
                        ContactsContract.CommonDataKinds.Email.DATA));
                //添加Email的信息
                emailAddress.replace(" ", "");
                emailList.add(emailAddress);
            }
            contact.setEmailList(emailList);
            emails.close();

            //查询==地址==类型的数据操作.StructuredPostal.TYPE_WORK
            Cursor address = contentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId,
                    null, null);
            while (address.moveToNext()) {
                String workAddress = address.getString(address.getColumnIndex(
                        ContactsContract.CommonDataKinds.StructuredPostal.DATA));

                //添加地址的信息
                contact.setAddress(workAddress);
            }
            address.close();

            //查询==公司 职位 昵称 备注==类型的数据操作
            String orgWhere = ContactsContract.Data.CONTACT_ID + " = ?";
            String[] orgWhereParams = new String[]{id};
            Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                    null, orgWhere, orgWhereParams, null);

            Log.d("123", orgCur.getCount() + "个");

            boolean isSetOrganization = false;
            boolean isSetNickName = false;
            boolean isSetRemark = false;

            while (orgCur.moveToNext()) {

                String type = orgCur.getString(orgCur.getColumnIndex(ContactsContract.Data.MIMETYPE));

                Log.d("123", type);

                if (!isSetOrganization && ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE.equals(type)) {
                    //组织名 (公司名字)
                    String company = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                    //职位
                    String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));

                    contact.setOrganization(company);
                    contact.setJob(title);

                    isSetOrganization = true;
                } else if (!isSetNickName && ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE.equals(type)) {
                    String nickName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.DATA));

                    //昵称
                    contact.setNickname(nickName);

                    isSetNickName = true;
                } else if (!isSetRemark && ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE.equals(type)) {
                    String remark = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

                    //备注
                    contact.setRemark(remark);
                    isSetRemark = true;
                }
            }
            orgCur.close();

            contactList.add(contact);
        }
        cursor.close();
        Log.d("123", contactList.size() + "");
        return contactList;
    }

}
