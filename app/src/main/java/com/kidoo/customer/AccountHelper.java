package com.kidoo.customer;

import android.app.Application;

import com.kidoo.customer.bean.User;
import com.kidoo.customer.utils.LogUtils;

/**
 * User: ShaudXiao
 * Date: 2017-09-19
 * Time: 19:11
 * Company: zx
 * Description: 账户信息
 * FIXME
 */


public class AccountHelper {

    private static final String TAG = AccountHelper.class.getSimpleName();
    private User mUser;
    private Application mApplication;

    private static AccountHelper sInstance;

    public AccountHelper(Application application) {
        this.mApplication = application;
    }

    public AccountHelper getInstance(Application application) {
        if(sInstance == null) {
            sInstance = new AccountHelper(application);
        } else {
            LogUtils.d(TAG, "init reload:");
        }

        return sInstance;
    }

    public static boolean isLogin() {
        return true;
    }

}
