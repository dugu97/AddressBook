package com.dugu.addressbook.util;

import android.provider.ContactsContract;

public class ContactSearchIndexUtil {
    //额外功能可能需要（做到时可查询api）
    //ContactsContract.CommonDataKinds.Identity  (可用于智能合并)
    //ContactsContract.CommonDataKinds.Note  （备注）

    //ContactsContract.CommonDataKinds.StructuredName
    private static final String[] STRUCTUREDNAME_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,      //用于展示的名字
            ContactsContract.CommonDataKinds.StructuredName.PHONETIC_GIVEN_NAME,        //姓名拼音
            //还有很多选项可选
    };

    //ContactsContract.CommonDataKinds.Photo
    private static final String[] PHOTO_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID,      //高分辨率照片文件的ID
            ContactsContract.CommonDataKinds.Photo.PHOTO,        //照片的缩略图 （二进制）
    };

    //ContactsContract.CommonDataKinds.Organization
    private static final String[] ORGANIZATION_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Organization.COMPANY,      //公司
            ContactsContract.CommonDataKinds.Organization.TITLE,        //职位
            //还有很多选项可选（如部门、职位描述等）
    };

    //ContactsContract.CommonDataKinds.Relation
    private static final String[] RELATION_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Relation.NAME,      //名字
            ContactsContract.CommonDataKinds.Relation.TYPE,        //关联人类型
            ContactsContract.CommonDataKinds.Relation.LABEL,        //关联人标签
    };

    //ContactsContract.CommonDataKinds.Event
    private static final String[] EVENT_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Event.START_DATE,      //日期
            ContactsContract.CommonDataKinds.Event.TYPE,        //多个类型 （生日、周年纪念等）
            ContactsContract.CommonDataKinds.Event.LABEL,        //事件标签
    };

    //ContactsContract.CommonDataKinds.Nickname
    private static final String[] NICKNAME_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Nickname.NAME,      //昵称
            ContactsContract.CommonDataKinds.Nickname.TYPE,        //多个类型，使用默认类型
            ContactsContract.CommonDataKinds.Nickname.LABEL,        //昵称标签
    };

    //ContactsContract.CommonDataKinds.GroupMembership
    private static final String[] GROUPMEMBERSHIP_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
            ContactsContract.CommonDataKinds.GroupMembership.GROUP_SOURCE_ID,
//            此组成员引用的组的sourceid。在插入行时，必须设置其中一个或GROUP_ROW_ID。
//    如果指定了此字段，则提供程序将首先尝试使用此group . source_id查找组。如果找到这样一个组，它将使用相应的行id。如果没有找到该组，它将创建一个。
    };


    //ContactsContract.CommonDataKinds.Phone
    private static final String[] PHONE_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.NUMBER,      //手机号
            ContactsContract.CommonDataKinds.Phone.TYPE,        //手机号类型 （多种，可自定义）
            ContactsContract.CommonDataKinds.Phone.LABEL,        //手机号标签
    };

    //ContactsContract.CommonDataKinds.Email
    private static final String[] EMAIL_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Email.ADDRESS,     //邮件地址
            ContactsContract.CommonDataKinds.Email.TYPE,        //邮件类型 （5种，包含可自定义）
            ContactsContract.CommonDataKinds.Email.LABEL,       //邮件标签
    };

    //ContactsContract.CommonDataKinds.Website
    private static final String[] WEBSITE_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Website.URL,       //网址url
            ContactsContract.CommonDataKinds.Website.TYPE,      //网址类型
            ContactsContract.CommonDataKinds.Website.LABEL,     //网址标签
    };

    //ContactsContract.CommonDataKinds.Im
    private static final String[] IM_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Im.DATA,       //联系方法的数据
            ContactsContract.CommonDataKinds.Im.TYPE,       //数据类型
            ContactsContract.CommonDataKinds.Im.LABEL,      //用户定义的数据标签
            ContactsContract.CommonDataKinds.Im.PROTOCOL,   //协议
            ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL,    //自定义协议
    };

    //ContactsContract.CommonDataKinds.StructuredPostal
    private static final String[] ADDRESS_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, //地址
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE,              //类型
            ContactsContract.CommonDataKinds.StructuredPostal.LABEL,             //标签
//            ContactsContract.CommonDataKinds.StructuredPostal.STARRED,           //街道
//            ContactsContract.CommonDataKinds.StructuredPostal.POBOX,             //盒子、抽屉、锁
//            ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD,      //街道附近表示（区分同名街道）
//            ContactsContract.CommonDataKinds.StructuredPostal.CITY,              //城市
//            ContactsContract.CommonDataKinds.StructuredPostal.REGION,            //区域（省，州）
//            ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE,          //邮编
//            ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY,          //国家
    };
}
