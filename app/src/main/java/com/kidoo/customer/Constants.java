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


    public static final String INIT_DATA_CACHE_KEY = "init_data";

    public static final String CHANNEL_DATA_CACHE_KEY = "channel_data";

    public static final int PAGE_SIZE = 5;

    public static final double DEFAAULT_AREN_CLOSE = 5 * 1000;
}
