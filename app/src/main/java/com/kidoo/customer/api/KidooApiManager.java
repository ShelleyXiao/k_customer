package com.kidoo.customer.api;

import android.app.Application;
import android.text.TextUtils;

import com.zhouyou.http.interceptor.HttpLoggingInterceptor;
import com.zhouyou.http.utils.HttpLog;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * User: ShaudXiao
 * Date: 2017-11-20
 * Time: 10:23
 * Company: zx
 * Description:
 * FIXME
 */


public class KidooApiManager {

    public static final int DEFAULT_MILLISECONDS = 60000;             //默认的超时时间
    private static final int DEFAULT_RETRY_COUNT = 3;                 //默认重试次数
    private static final int DEFAULT_RETRY_INCREASEDELAY = 0;         //默认重试叠加时间
    private static final int DEFAULT_RETRY_DELAY = 500;               //默认重试延时
    public static final int DEFAULT_CACHE_NEVER_EXPIRE = -1;          //缓存过期时间，默认永久缓存

    private static Application sContext;
    private static KidooApiManager singleton;

    private OkHttpClient.Builder okHttpClientBuilder;                 //okhttp请求的客户端
    private Retrofit.Builder retrofitBuilder;                         //Retrofit请求Builder


    private KidooApiManager() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier());
        okHttpClientBuilder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        retrofitBuilder = new Retrofit.Builder();
    }


    public static KidooApiManager getInstance() {
        testInitialize();
        if (singleton == null) {
            synchronized (KidooApiManager.class) {
                if (singleton == null) {
                    singleton = new KidooApiManager();
                }
            }
        }
        return singleton;
    }

    private static void testInitialize() {
        if (sContext == null)
            throw new ExceptionInInitializerError("请先在全局Application中调用 KidooHttpManger.init() 初始化！");
    }

    public static OkHttpClient getOkHttpClient() {
        return getInstance().okHttpClientBuilder.build();
    }

    public static Retrofit getRetrofit() {
        return getInstance().retrofitBuilder.build();
    }


    /**
     * 调试模式,默认打开所有的异常调试
     */
    public KidooApiManager debug(String tag) {
        debug(tag, true);
        return this;
    }

    /**
     * 调试模式,第二个参数表示所有catch住的log是否需要打印<br>
     * 一般来说,这些异常是由于不标准的数据格式,或者特殊需要主动产生的,
     * 并不是框架错误,如果不想每次打印,这里可以关闭异常显示
     */
    public KidooApiManager debug(String tag, boolean isPrintException) {
        String tempTag = TextUtils.isEmpty(tag)?"KidooApiManager_":tag;
        if(isPrintException){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(tempTag, isPrintException);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }

        HttpLog.customTagPrefix = tempTag;
        HttpLog.allowE = isPrintException;
        HttpLog.allowD = isPrintException;
        HttpLog.allowI = isPrintException;
        HttpLog.allowV = isPrintException;
        return this;
    }

    /**
     * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
     * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
     * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
     */
    public class DefaultHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
