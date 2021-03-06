package com.kidoo.customer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import com.kidoo.customer.api.ComParamContact;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.mvp.model.Customer;
import com.kidoo.customer.utils.LogUtils;

import net.oschina.common.helper.SharedPreferencesHelper;

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
    private Application application;

    private Customer mCustomer;

    private static AccountHelper sInstance;

    public AccountHelper(Application application) {
        this.application = application;
    }

    public static AccountHelper init(Application application) {
        if(sInstance == null) {
            sInstance = new AccountHelper(application);
        } else {
            LogUtils.d(TAG, "init reload:");
            sInstance.mCustomer = SharedPreferencesHelper.loadFormSource(sInstance.application, Customer.class);
        }

        return sInstance;
    }

    public static boolean isLogin() {
        return getUserId() > 0 && TokenManager.getInstance().isTokenVaild(ComParamContact.Token.AUTH_MODEL);
    }

    public static long getUserId() {
//        return getUser().getId();
        return 7;
    }

    public synchronized static Customer getUser() {
        if (sInstance == null) {
            LogUtils.w("AccountHelper instances is null, you need call init() method.");
            return new Customer();
        }
        if (sInstance.mCustomer == null)
            sInstance.mCustomer = SharedPreferencesHelper.loadFormSource(sInstance.application, Customer.class);
        if (sInstance.mCustomer == null)
            sInstance.mCustomer = new Customer();
        return sInstance.mCustomer;
    }

    public static boolean updateUserCache(Customer customer) {
        if (customer == null)
            return false;

        sInstance.mCustomer = customer;
        return SharedPreferencesHelper.save(sInstance.application, customer);
    }

    private static void clearUserCache() {
        sInstance.mCustomer = null;
        SharedPreferencesHelper.remove(sInstance.application, Customer.class);
    }

    public static boolean login(final Customer customer) {
        LogUtils.w("login:" + customer);
        int count = 10;
        boolean saveOk;
        // 保存缓存
        while (!(saveOk = updateUserCache(customer)) && count-- > 0) {
            SystemClock.sleep(100);
        }

        if(saveOk) {
            // 登陆成功后的动作
        }

        return saveOk;
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
