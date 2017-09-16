package com.kidoo.customer.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.kidoo.customer.AppContext;

/**
 * description: 獲取app 信息工具類
 * autour: ShaudXiao
 * date: 2017/9/16
 * update: 2017/9/16
 * version:
 */

public class AppSystemUtils {


    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pki = null;
        try {
            PackageManager pm = context.getPackageManager();
            pki = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pki;
    }

    /**
     * 获取application中指定的meta-data
     *
     * @param ctx the ctx
     * @param key the key
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (null == ctx || TextUtils.isEmpty(key)) {
            return null;
        }

        String resultData = null;
        try {
            PackageManager pm = ctx.getPackageManager();
            if (null != pm) {

                ApplicationInfo appInfo = pm.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (null != appInfo) {
                    if (null != appInfo.metaData) {
                        resultData = appInfo.metaData.getString(key);
                        if (resultData == null) {
                            resultData = appInfo.metaData.getInt(key) + "";
                        }
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * Gets version code.
     *
     * @return t获取版本号
     */
    public static int getVersionCode() {
        return getVersionCode(AppContext.context().getPackageName());
    }

    /**
     * Gets version code.
     *
     * @param packageName 包名
     * @return 获取版本号
     */
    public static int getVersionCode(String packageName) {
        try {
            return AppContext.context()
                    .getPackageManager()
                    .getPackageInfo(packageName, 0)
                    .versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            return 0;
        }
    }

    /**
     * Gets version name.
     *
     * @return 获取版本号
     */
    public static String getVersionName() {
        try {
            return AppContext
                    .context()
                    .getPackageManager()
                    .getPackageInfo(AppContext.context().getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            return "undefined version name";
        }
    }

    /**
     * 获取设备序列号
     *
     * @return 设备序列号
     */
    public static String getDeviceId() {
        return Build.SERIAL;
    }

}
