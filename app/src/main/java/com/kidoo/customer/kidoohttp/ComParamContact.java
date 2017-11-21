package com.kidoo.customer.kidoohttp;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 11:22
 * Company: zx
 * Description:
 * FIXME
 */

public class ComParamContact {

    public final static class Common {
        public final static String APPID = "appId";
        public final static String ACCESSTOKEN = "accessToken";
        public final static String TIMESTAMP = "timestamp";
        public final static String REFRESH_TOKEN = "refreshToken";
        public final static String SIGN = "sign";
    }

    public final static class Code {
        //http请求成功状态码
        public final static int HTTP_SUCESS = 0;
        //AccessToken错误或已过期
        public static final int ACCESS_TOKEN_EXPIRED = 100010101;
        //RefreshToken错误或已过期
        public static final int REFRESH_TOKEN_EXPIRED = 100010102;
        //帐号在其它手机已登录
        public static final int OTHER_PHONE_LOGINED = 100021006;
        //timestamp过期
        public static final int TIMESTAMP_ERROR = 100010104;
        //缺少授权信息,没有accessToken,应该是没有登录
        public final static int NO_ACCESS_TOKEN = 100010100;
        //签名错误
        public final static int ERROR_SIGN = 100010105;
        //设备未绑定
        public final static int DEVICE_NO_BIND = 100022001;
    }

    public final static class Token {
        public final static String AUTH_MODEL = "authModel";
    }

    public final static class QnToken {

        public final static String TEST_BASE = "http://ouhstqlop.bkt.clouddn.com/";

        public final static String AUTH_MODEL = "qn_authModel";

        public final static String PATH = "/customer/common/getInitData";
    }

    public final static class UserStatus {
        public final static String PATH = "";
    }

    public static final class TempKey {
        public final static String PATH = "/customer/common/getKeyPair";
    }

    public final static class Login {
        public final static String PATH = "/customer/common/login";
        public final static String ACCOUNT = "mobile";
        public final static String PASSWORD = "pwd";
        public final static String LOGINTYPE = "loginType";
    }

    public final static class MyBroadcast {
        public final static String PATH = "/customer/broadcast/queryMyBroadcast";
        public final static String USER_ID = "customerId";
    }

    public final static class MyCompaign {
        public final static String PATH = "/customer/campaign/queryMyCampaign";
        public final static String USER_ID = "customerId";
    }

    public final static class TeamBaseInfo {
        public final static String PATH = "/customer/org/queryTeamBaseInfo";
        public final static String USER_ID = "customerId";
        public final static String TEAM_IDS = "teamIds";
    }

    public final static class BroadcastDetail {
        public final static String PATH = "/customer/broadcast/queryDetail";
        public final static String USER_ID = "customerId";
        public final static String BROADCAST_ID = "broadcastId";
    }
}
