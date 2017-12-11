package com.kidoo.customer;

import android.os.Environment;

import java.io.File;

/**
 * Created by zhangbing on 2016/8/3.
 */
public class Constants {



    //================= KEY ====================

//    public static final String KEY_API = "f95283476506aa756559dd28a56f0c0b"; //需要APIKEY请去 http://apistore.baidu.com/ 申请,复用会减少访问可用次数
    public static final String KEY_API = "52b7ec3471ac3bec6846577e79f20e4c"; //需要APIKEY请去 http://www.tianapi.com/#wxnew 申请,复用会减少访问可用次数
    public static final String WEIXNAPP_ID = "wx11ef503bfa72cb61";

    //================= PATH ====================


    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "zhangbing" + File.separator + "perifit";

    //================= UMENG ====================

    public static final String EVENT_TAB_HOME = "tab_home";

    //================= PREFERENCE ====================

    public static final String SP_NIGHT_MODE = "night_mode";

    public static final String SP_NO_IMAGE = "no_image";

    public static final String SP_AUTO_CACHE = "auto_cache";

    public static final String SP_CURRENT_ITEM = "current_item";

    public static final String SP_LIKE_POINT = "like_point";

    public static final String UMENG_PUSH_TOKEN = "umeng_push_token";

    public static final String AUTH_CODE = "auth_code";

    public static final String AUTH_ID = "1";

    //发送验证码 : 1 注册短信 2密码找回短信 3活动报名短信
    public static final int REGIST_CODE = 1;

    public static final int FORGOTPASS = 2;

    public static final int JOYINACTIVITY = 3;

    //================= ENROLL ====================
    public static final String ENROLL_TYPE = "ENROlL_TYPE";
    public static final int ENROLL_TYPE_MATCH = 0x302;
    public static final int ENROLL_TYPE_ACTIVITY = 0x303;

    public static final String ENROLL_TITLE = "0";

    public static final String ENROLL_DATE = "1";

    public static final String ENROLL_PRICE = "2";

    public static final String ENROLL_COUNT = "3";

    public static final String ENROLL_TOTALCOUNT = "4";

    public static final String ENROLL_PLACE = "5";

    public static final String ENROLL_ACTIVITYID = "6";
    public static final String SP_SHORTCUT = "short_cut";
    public static final String EXTRA_FROM = "extra_icon";
    public static final  boolean EXTRA_FROM_SHORTCUT_ICON = true ;
    public static final String REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    public static final String SCOPE = "";
    public static final String PREVIEWIMAG_SELECT = "photo_preview_select";
    public static final String INTENT_ISLIKE = "islike";
    public static final String INTENT_ISLIKE_POSITION ="islike_position" ;
    public static final String INTENT_ISLIKE_POST_TYPE = "islike_type";
    public static final int TYPE_POSTDEFORMPOST = 0x107;
    public static final int TYPE_POSTDEFORMLIKE = 0x108;
    public static final int QATYPE_STAR = 0x118;
    public static final int QATYPE_SCORE= 0x119;
    public static final int QATYPE_GAME= 0x120;
    public static final String TYPE = "type";
    public static final String POST_ID = "postId";
    public static final String INTENT_GAMECATEGORY = "GAME_CATEGORY_ID";
    public static final String INTENT_GAME_NAME = "game_name";


    public static final int PAGE_SIZE = 5;
}
