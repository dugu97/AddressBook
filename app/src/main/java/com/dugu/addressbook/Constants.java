package com.dugu.addressbook;

public class Constants {

    // 初始化随机颜色表
    public static final int[] COLOR_PROJECT = {0xff5F6DB8, 0xff4DBC70, 0xff12A988, 0xff508DCE, 0xff9A32CD, 0xff4F8CCD};

    // 初始化主界面长按选择列表
    public static final String[] CONTACT_LONG_CLICK_OPERATION_PROJECT = {"编辑", "删除", "分享联系人", "加入黑名单", "创建快捷方式"};

    // 初始化新建联系人模式选择列表
    public static final String[] MODE_CHOOSE_PROJECT = {"手机联系人", "SIM联系人"};

    // 初始化手机号码标签列表
    public static final String[] PHONE_LABEL_PROJECT = {"手机", "住宅", "单位", "其它"};

    // 初始化邮件标签列表
    public static final String[] EMAIL_LABEL_PROJECT = {"私人", "单位", "其它"};

    //初始化群组 group_id（数组序号） 及其 名称;
    public static final String[] GROUP_PROJECT = {"工作", "好友", "家人", "手机联系人", "SIM", "名片夹"};
    public static final int GROUP_SIM = 4;

    //主界面工具Item的边界 (工具栏的contact_id为负数)
    public static final int LIST_UTIL_INDEX = 0;

    //新建和编辑联系人的模式选择
    public static final int CONTACT_MODE_NEW_PHONE_CONTACT = 1;
    public static final int CONTACT_MODE_EDIT_PHONE_CONTACT = 2;
    public static final int CONTACT_MODE_NEW_SMS_CONTACT = 3;
    public static final int CONTACT_MODE_EDIT_SMS_CONTACT = 4;

    //联系人信息Item排序字段
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

    //所有Activity都可使用的通讯字段
    public static final String ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT = "new_or_edit_contact_mode";  //用于新建或者编辑联系人的模式选择
    public static final String ALLACTIVITY_CONTACT_ID = "all_activity_contact_id";  //用于新建或者编辑联系人的模式选择(与上一个字段搭配使用)

}
