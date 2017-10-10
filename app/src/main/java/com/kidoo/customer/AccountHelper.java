package com.kidoo.customer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

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

    public static final String HOLD_ACCOUNT = "holder_account";
    public static final String HOLD_USERNAME_KEY = "holdUsernameKey";

    private static final String TAG = AccountHelper.class.getSimpleName();
    private User mUser;
    private Application mApplication;

    private static AccountHelper sInstance;

    public AccountHelper(Application application) {
        this.mApplication = application;
    }

    public static AccountHelper getInstance(Application application) {
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

    public static String getAccount(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AccountHelper.HOLD_ACCOUNT, Context.MODE_PRIVATE);
        String holdUsername = sp.getString(HOLD_USERNAME_KEY, null);

        return holdUsername;
    }

    public static void holdAccount(Context context, String userName) {
        String username = userName.trim();
        if (!TextUtils.isEmpty(username)) {
            SharedPreferences sp = context.getSharedPreferences(AccountHelper.HOLD_ACCOUNT, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(HOLD_USERNAME_KEY, username);
            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        }
    }

}
