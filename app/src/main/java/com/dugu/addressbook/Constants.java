package com.dugu.addressbook;

public class Constants {

    // 初始化主界面长按选择列表
    public static final String[] CONTACT_LONG_CLICK_OPERATION_PROJECT = {"编辑", "删除", "分享联系人", "加入黑名单"};
    public static final String[] CONTACT_LONG_CLICK_OPERATION_PROJECT_2 = {"编辑", "删除", "分享联系人", "移除黑名单"};
    public static final int CONTACT_LONG_CLICK_OPERATION_EDIT = 0;
    public static final int CONTACT_LONG_CLICK_OPERATION_DELETE = 1;
    public static final int CONTACT_LONG_CLICK_OPERATION_SHARE_CONTACT= 2;
    public static final int CONTACT_LONG_CLICK_OPERATION_ADD_OR_DELETE_BLACK = 3;

    //初始化联系人详情 更多 的选择列表
    public static final String[] CONTACT_DETAIL_MORE_OPERATION_PROJECT = {"删除", "分享联系人"};
    public static final int CONTACT_DETAIL_MORE_OPERATION_DELETE = 0;
    public static final int CONTACT_DETAIL_MORE_OPERATION_SHARE = 1;



    //初始化群组详情 更多 的选择列表
    public static final String[] GROUP_DETAIL_MORE_OPERATION_PROJECT = {"移除成员", "重命名"};
    public static final int GROUP_DETAIL_MORE_OPERATION_DELETE_CONTACT = 0;
    public static final int GROUP_DETAIL_MORE_OPERATION_RENAME_GROUP = 1;


    // 初始化联系人照片选择列表
    public static final String[] MODE_TAKE_PHOTO = {"拍照", "从图库中选择"};
    public static final int TAKE_PHOTO_FROM_CAMERA = 0;
    public static final int TAKE_PHOTO_FROM_ALBUM = 1;

    // 初始化手机号码标签列表
    public static final String[] PHONE_LABEL_PROJECT = {"手机", "住宅", "单位", "其它", "自定义"};

    // 初始化邮件标签列表
    public static final String[] EMAIL_LABEL_PROJECT = {"私人", "单位", "其它", "自定义"};

    // 手机号码 群组 标签最大长度
    public static final int LABEL_MAX_LENGTH = 6;


    //初始化群组 group_id（数组序号） 及其 名称;
    public static final String[] GROUP_PROJECT = {"手机联系人", "名片夹", "黑名单", "工作", "好友", "家人"};
    public static final int GROUP_PHONE = 0;
    public static final int GROUP_CARD = 1;
    public static final int GROUP_BLACK = 2;

    //主界面工具Item的边界 (工具栏的contact_id为负数)
    public static final int LIST_UTIL_INDEX = 0;
    public static final int UTIL_GROUP_INDEX = -9;
    public static final int UTIL_BUSINESS_CARD_INDEX = -5;

    //新建和编辑联系人的模式选择
    public static final int CONTACT_MODE_NEW_PHONE_CONTACT = 1;
    public static final int CONTACT_MODE_EDIT_PHONE_CONTACT = 2;
    public static final int CONTACT_MODE_NEW_PHONE_CONTACT_WITH_QR_CODE = 3;

    //选择联系人的模式选择（用于ContactChooseActivity）
    public static final String MODE_CONTACTCHOOSE_ACTIVITY = "mode_choose";
    public static final int CONTACT_MODE_WITHOUT_GROUP = 1;  //选择群组之外的联系人
    public static final int CONTACT_MODE_WITHIN_GROUP = 2;   //选择群组里面的联系人


    //Activity之间的请求码
    public static final int REQUEST_CODE_CHOOSE_GROUP = 1;
    public static final int REQUEST_CODE_NEW_CONTACT = 2;
    public static final int REQUEST_CODE_EDIT_CONTACT = 3;
    public static final int REQUEST_CODE_CHOOSE_CONTACT = 4;

    //Activity之间的返回码
    public static final int RESULT_CODE_OK = 99;
    public static final int RESULT_CODE_FAILURE = 98;


    //联系人信息Item排序字段
    public static final int SORTKEY_BUSINESS_CARD = 1;
    public static final int SORTKEY_PHONE = 2;
    public static final int SORTKEY_EMAIL = 3;
    public static final int SORTKEY_NICKNAME = 4;
    public static final int SORTKEY_ADDRESS = 5;
    public static final int SORTKEY_BIRTHDAY = 6;
    public static final int SORTKEY_GROUP = 7;
//    public static final int SORTKEY_RING = 8;
    public static final int SORTKEY_REMARK = 9;

    //mainActivity通讯字段
    public static final String MAINACTIVITY_CONTACT_ID = "contact_id";

    //GroupActivity通讯字段
    public static final String GROUPACTIVITY_GROUP_ID = "group_id";
    public static final String GROUPACTIVITY_GROUP_NAME = "group_name";

    //ImportAndExportActivity通讯字段
    public static final String IMPORTANDEXPORT_ACTIVITY_CONTACTS = "cotacts_data";

    //所有Activity都可使用的通讯字段
    public static final String ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT = "new_or_edit_contact_mode";  //用于新建或者编辑联系人的模式选择
    public static final String ALLACTIVITY_CONTACT_ID = "all_activity_contact_id";  //用于新建或者编辑联系人的模式选择(与上一个字段搭配使用)
    public static final String ALLACTIVITY_GROUPS_CHOOSE = "groups_choose";
    public static final String ALLACTIVITY_GROUP_ID = "group_id";
    public static final String ALLACTIVITY_STRING = "strings";
}
