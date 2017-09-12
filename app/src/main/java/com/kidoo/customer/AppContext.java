package com.kidoo.customer;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.kidoo.customer.utils.CommonUtils;
import com.kidoo.customer.widget.SimplexToast;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * User: ShaudXiao
 * Date: 2017-09-12
 * Time: 17:44
 * Company: kidoo
 * Description: 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * FIXME
 */

public class AppContext extends Application{

    private static Context _context;

    private RefWatcher mRefWatcher;
    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();

        initBugly();

        mRefWatcher = BuildConfig.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;

    }

    private void initBugly() {
        Context context = getApplicationContext();

        String packageName = context.getPackageName();
        String processName = CommonUtils.getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(context, "182ff3e5bd", true, strategy);
    }

    public static synchronized AppContext context() {
        return (AppContext) _context;
    }

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, Gravity.BOTTOM);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.BOTTOM);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }


    public static void showToast(String message, int duration, int icon, int gravity) {
        Context context = _context;
        if (context != null)
            SimplexToast.show(context, message, gravity, duration);
    }


}