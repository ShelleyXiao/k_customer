package com.kidoo.customer;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.kidoo.customer.api.KidooApiService;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelCMap;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.cache.ACache;
import com.kidoo.customer.di.Component.AppComponent;
import com.kidoo.customer.di.Component.DaggerAppComponent;
import com.kidoo.customer.di.module.AppModule;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.model.HttpHeaders;
import com.kidoo.customer.kidoohttp.http.model.HttpParams;
import com.kidoo.customer.service.GloablCheckService;
import com.kidoo.customer.utils.AppSystemUtils;
import com.kidoo.customer.utils.CommonUtils;
import com.kidoo.customer.utils.TDevice;
import com.kidoo.customer.widget.SimplexToast;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User: ShaudXiao
 * Date: 2017-09-12
 * Time: 17:44
 * Company: kidoo
 * Description: 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * FIXME
 */

public class AppContext extends Application {

    private static Context _context;

    //    private RefWatcher mRefWatcher;
    private static AppContext instance;

    public AppComponent mAppComponent;

    private InitData mInitData;


    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();

        initApplicationComponent();

//        initBugly();

        initHttp();

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

//        mRefWatcher = BuildConfig.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
        AccountHelper.init(this);

        GloablCheckService.init(_context);

        SimplexToast.init(this);

    }

    private List<ChannelA> gChannelAList = new ArrayList<>();

    private List<ChannelCMap> gChannelCMaps = new ArrayList<>();

    private void initBugly() {
        Context context = getApplicationContext();

        String packageName = context.getPackageName();
        String processName = CommonUtils.getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(context, "182ff3e5bd", true, strategy);
    }

    private void initHttp() {
        KidooApiManager.init(this);

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        //设置公共请求参数
        HttpParams params = new HttpParams();
//        if(!TextUtils.isEmpty(String.valueOf(TDevice.getTelephoneNumber(this)))) {
////            params.put("mobile", TDevice.getTelephoneNumber(this));
//        }
        params.put("version", String.valueOf(AppSystemUtils.getVersionCode()));
        params.put("mobileModel", "1");
        params.put("modelDetail", TDevice.getSystemModel());

        KidooApiManager.getInstance()
                .debug("KidooHttp", true)
                .setReadTimeOut(5 * 1000)
                .setWriteTimeOut(5 * 1000)
                .setConnectTimeout(5 * 1000)
                .setRetryCount(3)//默认网络不好自动重试3次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(KidooApiService.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCommonHeaders(headers)//设置全局公共头
                .addCommonParams(params);//设置全局公共参数

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

    private void initApplicationComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();
    }

    /**
     * 对外提供AppComponent
     *
     * @return
     */
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public InitData getInitData() {
        if(mInitData == null) {
            mInitData = (InitData)ACache.get(this).getAsObject(Constants.INIT_DATA_CACHE_KEY);
        }
        return mInitData;
    }

    public void setInitData(InitData mInitData) {

        this.mInitData = mInitData;
        ACache.get(this).put(Constants.INIT_DATA_CACHE_KEY, mInitData);
    }


    public List<ChannelA> getgChannelAList() {
        if(gChannelAList == null) {
            gChannelAList = (ArrayList<ChannelA>)ACache.get(this).getAsObject(Constants.CHANNEL_DATA_CACHE_KEY);
        }

        return gChannelAList;
    }

    public void setgChannelAList(List<ChannelA> dataList) {

        ACache.get(this).put(Constants.CHANNEL_DATA_CACHE_KEY, (ArrayList<ChannelA>)dataList);

        this.gChannelAList.clear();
        this.gChannelAList.addAll(dataList);
    }

    public List<ChannelCMap> getgChannelCMaps() {
        if(gChannelCMaps == null) {
            gChannelCMaps = (ArrayList<ChannelCMap>)ACache.get(this).getAsObject(Constants.CHANNELCMAP_DATA_CACHE_KEY);
        }

        return gChannelCMaps;
    }

    public void setgChannelCMaps(List<ChannelCMap> datas) {
        ACache.get(this).put(Constants.CHANNELCMAP_DATA_CACHE_KEY, (ArrayList<ChannelCMap>)datas);

        this.gChannelCMaps.clear();
        this.gChannelCMaps.addAll(datas);
    }
}
