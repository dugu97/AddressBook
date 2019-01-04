package com.dugu.addressbook;

public class Constants {

    // 初始化随机颜色表
    public static final int[] COLOR_PROJECT = {0xff5F6DB8, 0xff4DBC70, 0xff12A988, 0xff508DCE, 0xff9A32CD, 0xff4F8CCD};

    // 初始化主界面长按选择列表
    public static final String[] MAINFRAGOPERATION_PROJECT = {"编辑", "删除", "分享联系人", "加入黑名单", "创建快捷方式"};


    //初始化群组 group_id（数组序号） 及其 名称;
    public static final String[] GROUP_PROJECT = {"工作", "好友", "家人", "手机联系人", "SIM", "名片夹"};
    public static final int GROUP_SIM = 4;

    //主界面工具Item的边界 (工具栏的contact_id为负数)
    public static final int LIST_UTIL_INDEX = 0;

    //联系人信息详情Item排序字段
    public static final int SORTKEY_PHONE = 1;
    public static final int SORTKEY_EMAIL = 2;
    public static final int SORTKEY_NICKNAME = 3;
    public static final int SORTKEY_ADDRESS = 4;
    public static final int SORTKEY_BIRTHDAY = 5;
    public static final int SORTKEY_GROUP = 6;
    public static final int SORTKEY_RING = 7;
    public static final int SORTKEY_REMARK = 8;

    //Activity通讯字段
    public static final String MAINACTIVITY_CONTACT_ID = "contact_id";
    public static final String MAINACTIVITY_CONTACT_NAME = "nameOrPhone";
    public static final String MAINACTIVITY_ORGANIZATION = "organization";
    public static final String MAINACTIVITY_JOB = "job";
    public static final String MAINACTIVITY_ICON = "icon";

}
