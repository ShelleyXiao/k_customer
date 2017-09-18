package com.kidoo.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.kidoo.customer.utils.AppSystemUtils;

import net.oschina.common.utils.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * User: ShaudXiao
 * Date: 2017-09-12
 * Time: 19:38
 * Company: kidoo
 * Description: 保存全局配置(配置文件目录 )
 * FIXME
 */


public class AppConfig {
    private final static String APP_CONFIG = "config";

    private static final String KEY_VERSION_CODE = "versionCode";
    public static final String KEY_APP_UNIQUE_ID = "appUniqueID";
    private static final String KEY_SYSTEM_CONFIG_TIMESTAMP = "systemConfigTimeStamp";
    private static final String KEY_LOCATION_INFO = "locationInfo";
    private static final String KEY_LOCATION_PERMISSION = "locationPermission";
    private static final String KEY_LOCATION_APP_CODE = "locationAppCode";

    private Context mContext;
    private static AppConfig appConfig;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

    public String get(String key) {
        Properties props = get();
        return (props != null ) ? props.getProperty(key) : null;
    }

    public Properties get() {
        FileInputStream fis = null;
        Properties properties = new Properties();
        try {
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath() + File.separator + APP_CONFIG);

            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return properties;
    }

    public void set(String key, String value){
        Properties props = get();
        props.setProperty(key, value);
        setProps(props);
    }

    private void setProps(Properties p) {
        FileOutputStream fos = null;
        try {
            // 把config建在(自定义)app_config的目录下
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            File conf = new File(dirConf, APP_CONFIG);
            fos = new FileOutputStream(conf);

            p.store(fos, null);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(fos);
        }
    }

    public void remove(String... key) {
        Properties props = get();
        for (String k : key)
            props.remove(k);
        setProps(props);
    }
//////////////////////// sharePrefernces

    public static SharedPreferences getSettingPreferences(Context context) {
        return context.getSharedPreferences(AppConfig.class.getName(), Context.MODE_PRIVATE);
    }

    public static boolean checkIsNewVersion(Context context) {
        int saveVersionCode = getSaveVersionCode(context);
        int currentVersionCode = AppSystemUtils.getVersionCode();
        if (saveVersionCode < currentVersionCode) {
            updateSaveVersionCode(context, currentVersionCode);
            return true;
        }
        return false;
    }

    public static int getSaveVersionCode(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getInt(KEY_VERSION_CODE, 0);
    }

    private static int updateSaveVersionCode(Context context, int version) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putInt(KEY_VERSION_CODE, version);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        return version;
    }

    public static void updateSystemConfigTimeStamp(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putLong(KEY_SYSTEM_CONFIG_TIMESTAMP,
                System.currentTimeMillis());
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static long getSystemConfigTimeStamp(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getLong(KEY_SYSTEM_CONFIG_TIMESTAMP, 0);
    }

    public static void updateLocationInfo(Context context, boolean hasLocation) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putBoolean(KEY_LOCATION_INFO, hasLocation);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static boolean hasLocation(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getBoolean(KEY_LOCATION_INFO, false);
    }

    public static void updateLocationPermission(Context context, boolean hasPermission) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putBoolean(KEY_LOCATION_PERMISSION, hasPermission);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static boolean hasLocationPermission(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getBoolean(KEY_LOCATION_PERMISSION, false);
    }

    public static void updateLocationAppCode(Context context, int appCode) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putInt(KEY_LOCATION_APP_CODE, appCode);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static int hasLocationAppCode(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getInt(KEY_LOCATION_APP_CODE, 0);
    }
}
