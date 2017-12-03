package com.kidoo.customer.service;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.mvp.contract.InitDataContract;
import com.kidoo.customer.mvp.interactor.InitDataInteractor;
import com.kidoo.customer.utils.AppSystemUtils;
import com.kidoo.customer.utils.LogUtils;

/**
 * description: 获取全局参数， token 检查
 * autour: ShaudXiao
 * date: 2017/12/2
 * update: 2017/12/2
 * version:
*/

public class GloablCheckService extends Service {

    private static final String FLAG_ACTION_FIRST = "_FIRST";
    private static final String FLAG_ACTION_EXIT = "_EXIT";

    public static void init(Context context) {
        if(!AccountHelper.isLogin()) {
            return;
        }

        startAction(context);
    }

    static void startAction(Context context) {
        Intent intent = new Intent(context, GloablCheckService.class);
        intent.setAction(FLAG_ACTION_FIRST);
        context.startService(intent);
        LogUtils.i("startAction");
    }

    static void exitAction(Context context) {
        Intent intent = new Intent(context, GloablCheckService.class);
        intent.setAction(FLAG_ACTION_EXIT);
        context.startService(intent);
        LogUtils.i("exitAction");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            LogUtils.i("onStartCommand: intent is null.");
            return super.onStartCommand(null, flags, startId);
        }
        String action = intent.getAction();
        LogUtils.i("onStartCommand:" + action);
        if (action != null) {
            handleAction(action, intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void handleAction(String action, Intent intent) {
        if(FLAG_ACTION_FIRST.equals(action)) {
            doRequestInitData();
        }
    }

    private void doRequestInitData() {
        InitDataInteractor initDataInteractor = new InitDataInteractor();

        initDataInteractor.doGetInitData(AppSystemUtils.getVersionCode() + "", "2", new InitDataContract.Interactor.InitDataCallback() {
            @Override
            public void onSuccess(InitData result) {
                LogUtils.w("request init data success");
                Application application =  getApplication();
                if(application instanceof AppContext) {
                    ((AppContext)application).setInitData(result);
                }
            }

            @Override
            public void onFailure(String msg) {
                LogUtils.e("request init data faild: " + msg);
            }
        });
    }


}
