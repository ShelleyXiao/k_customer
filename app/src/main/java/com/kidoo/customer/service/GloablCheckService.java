package com.kidoo.customer.service;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.api.CheckTokenIdApi;
import com.kidoo.customer.api.token.AuthModel;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.CheckTokenIdBean;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.InitDataContract;
import com.kidoo.customer.mvp.contract.channelCampaign.ChannelCampaignContract;
import com.kidoo.customer.mvp.interactor.account.InitDataInteractor;
import com.kidoo.customer.utils.AppSystemUtils;
import com.kidoo.customer.utils.LogUtils;

import java.util.Date;

import io.reactivex.functions.Consumer;

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

    private static final String FLAG_ACTION_REFRESH_TOKENID = "_REFRESH";

    private final static int ALARM_INTERVAL_SECOND = 3 * 60 * 1000; //3600000;

    private long lastCheckIdTime = -1;

    private AlarmManager mAlarmMgr;

    public static void init(Context context) {
//        if(!AccountHelper.isLogin()) {
//            return;
//        }

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

    public static void startRefreshTokenID(Context context) {
        Intent intent = new Intent(context, GloablCheckService.class);
        intent.setAction(FLAG_ACTION_REFRESH_TOKENID);
        context.startService(intent);
        LogUtils.w("FLAG_ACTION_REFRESH_TOKENID");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAlarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
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


    private void registerNextAlarm() {
        cancelRequestAlarm();
        mAlarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + ALARM_INTERVAL_SECOND, ALARM_INTERVAL_SECOND, getOperationIntent());

        LogUtils.i("registerAlarmByInterval interval:" + ALARM_INTERVAL_SECOND);
    }

    private void cancelRequestAlarm() {
        mAlarmMgr.cancel(getOperationIntent());
    }


    private PendingIntent getOperationIntent() {
        Intent intent = new Intent(this, GloablCheckService.class);
        intent.setAction(FLAG_ACTION_REFRESH_TOKENID);
        return PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void handleAction(String action, Intent intent) {
        if (FLAG_ACTION_FIRST.equals(action)) {
            doRequestInitData();
        } else if (FLAG_ACTION_EXIT.equals(action)) {
            cancelRequestAlarm();
            stopSelf();
        } else if (FLAG_ACTION_REFRESH_TOKENID.equals(action)) {
            doRequestTokenId();
        }

    }

    private void doRequestInitData() {
        InitDataInteractor initDataInteractor = new InitDataInteractor();

        initDataInteractor.doGetInitData(AppSystemUtils.getVersionCode() + "", "2", new InitDataContract.Interactor.InitDataCallback() {
            @Override
            public void onSuccess(InitData result) {
                LogUtils.w("request init data success");
                Application application = getApplication();
                if (application instanceof AppContext) {
                    ((AppContext) application).setInitData(result);
                }
            }

            @Override
            public void onFailure(String msg) {
                LogUtils.e("request init data faild: " + msg);
            }
        });

        initDataInteractor.queryAllChannelsAction(new ChannelCampaignContract.Interactor.GetAllChannelsCallback() {
            @Override
            public void onSuccess(AllChannelResultBean result) {
                if (result != null) {
                    if (result.getChannelAList() != null) {
                        AppContext.context().setgChannelAList(result.getChannelAList());
                        AppContext.context().setgChannelCMaps(result.getChannelCmaps());
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void doRequestTokenId() {
        long time = new Date().getTime();
        if (time - lastCheckIdTime < 3000) {
            return;
        }

        LogUtils.w("doRequestTokenId");

        lastCheckIdTime = time;

        String customId = String.valueOf(AccountHelper.getUserId());
        final AuthModel model = TokenManager.getInstance().getAuthModel(TokenManager.KEY_AUTH);
        CheckTokenIdApi.checkTokenId(customId, model.getTokenId())
                .subscribe(new Consumer<KidooApiResult<CheckTokenIdBean>>() {
                    @Override
                    public void accept(KidooApiResult<CheckTokenIdBean> checkTokenIdBeanKidooApiResult) throws Exception {
                        if (checkTokenIdBeanKidooApiResult.isSuccess()) {
                            long time = new Date().getTime();
                            long serverTime = checkTokenIdBeanKidooApiResult.getData().getServerTime();
                            long difTime = time - serverTime;
                            LogUtils.i(difTime);

                            model.setGetTokenTime(time);
                            model.setDifTime(difTime);
                            model.setServerTime(serverTime);
                            TokenManager.getInstance().updateAuthModel(TokenManager.KEY_AUTH, model);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        registerNextAlarm();
    }


}
