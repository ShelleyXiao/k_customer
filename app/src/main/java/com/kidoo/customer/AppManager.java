package com.kidoo.customer;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * User: ShaudXiao
 * Date: 2017-09-12
 * Time: 19:16
 * Company: kidoo
 * Description:
 * FIXME
 */


public class AppManager {

    private static Stack<Activity> sActivityStack;
    private static AppManager mInstance;

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (null == mInstance) {
            mInstance = new AppManager();
        }
        return mInstance;
    }


    public void addStack(Activity activity) {
        if (null == sActivityStack) {
            sActivityStack = new Stack<>();
        }

        sActivityStack.add(activity);
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity act = sActivityStack.lastElement();
        finishActivity(act);
    }

    /**
     * 结束指定Activity
     */
    public void finishActivity(Activity activity) {
        if (null != activity) {
            sActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishAllActivity() {
        for(int i = 0; i < sActivityStack.size(); i++) {
            Activity activity = sActivityStack.get(i);
            if(null != activity) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        sActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();

            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
         //   System.exit(0);
        } catch (Exception e) {
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
        }
    }

}
