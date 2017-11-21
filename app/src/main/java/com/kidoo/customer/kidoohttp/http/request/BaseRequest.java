package com.kidoo.customer.kidoohttp.http.request;

import android.content.Context;
import android.text.TextUtils;

import com.kidoo.customer.kidoohttp.http.ApiService;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.interceptor.HeadersIntercptor;
import com.kidoo.customer.kidoohttp.http.model.HttpHeaders;
import com.kidoo.customer.kidoohttp.http.model.HttpParams;
import com.kidoo.customer.kidoohttp.http.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * User: ShaudXiao
 * Date: 2017-11-20
 * Time: 16:28
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseRequest<R extends BaseRequest> {

    protected Context context;

    protected String baseUrl;
    protected String url;

    protected long readTimeOut;
    protected long writeTimeOut;
    protected long connectTimeOut;
    protected int retryCount;
    protected int retryDelay;
    protected int tretryIncreaseDelay;

    protected List<Cookie> cookies = new ArrayList<>();
    protected final List<Interceptor> networkInterceptors = new ArrayList<>();
    protected HttpHeaders headers = new HttpHeaders();
    protected HttpParams params = new HttpParams();

    protected ApiService apiManager;
    protected OkHttpClient okHttpClient;

    protected HttpUrl httpUrl;

    protected List<Converter.Factory> converterFactories = new ArrayList<>();
    protected List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
    protected final List<Interceptor> interceptors = new ArrayList<>();

    protected Retrofit retrofit;

    public BaseRequest(String url) {
        this.url = url;
        context = KidooApiManager.getContext();
        KidooApiManager config = KidooApiManager.getInstance();
        this.baseUrl = config.getBaseUrl();
        if(!TextUtils.isEmpty(this.baseUrl)) {
            httpUrl = HttpUrl.parse(this.baseUrl);
        }
        retryCount = config.getRetryCount();
        retryDelay = config.getRetryDelay();
        tretryIncreaseDelay = config.getRetryIncreaseDelay();

        //默认添加 Accept-Language
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if(!TextUtils.isEmpty(acceptLanguage)) {
            headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        }
        //默认添加 User-Agent
        String userAgent = HttpHeaders.getUserAgent();
        if(!TextUtils.isEmpty(userAgent)) {
            headers(HttpHeaders.HEAD_KEY_USER_AGENT, userAgent);
        }

        if(config.getCommonHeaders() != null) {
            headers.put(config.getCommonHeaders());
        }

        if(config.getCommonParams() != null) {
            params.put(config.getCommonParams());
        }

    }

    public HttpParams getParams() {
        return this.params;
    }

    public R readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return (R) this;
    }

    public R writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return (R) this;
    }

    public R connectTimeout(long connectTimeout) {
        this.connectTimeOut = connectTimeout;
        return (R) this;
    }

    public R baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        if (!TextUtils.isEmpty(this.baseUrl))
            httpUrl = HttpUrl.parse(baseUrl);
        return (R) this;
    }

    public R retryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");
        this.retryCount = retryCount;
        return (R) this;
    }

    public R retryDelay(int retryDelay) {
        if (retryDelay < 0) throw new IllegalArgumentException("retryDelay must > 0");
        this.retryDelay = retryDelay;
        return (R) this;
    }

    public R retryIncreaseDelay(int retryIncreaseDelay) {
        if (retryIncreaseDelay < 0)
            throw new IllegalArgumentException("retryIncreaseDelay must > 0");
        this.tretryIncreaseDelay = retryIncreaseDelay;
        return (R) this;
    }

    public R addInterceptor(Interceptor interceptor) {
        interceptors.add(Utils.checkNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }

    public R addNetworkInterceptor(Interceptor interceptor) {
        networkInterceptors.add(Utils.checkNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }

    /**
     * 设置Converter.Factory,默认GsonConverterFactory.create()
     */
    public R addConverterFactory(Converter.Factory factory) {
        converterFactories.add(factory);
        return (R) this;
    }

    /**
     * 设置CallAdapter.Factory,默认RxJavaCallAdapterFactory.create()
     */
    public R addCallAdapterFactory(CallAdapter.Factory factory) {
        adapterFactories.add(factory);
        return (R) this;
    }

    /**
     * 添加头信息
     */
    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R)this;
    }

    /**
     * 添加头信息
     */
    public R headers(String key, String val) {
        headers.put(key, val);
        return (R)this;
    }

    /**
     * 移除头信息
     */
    public R removeHeader(String key) {
        headers.remove(key);
        return (R) this;
    }

    /**
     * 移除所有头信息
     */
    public R removeAllHeaders() {
        headers.clear();
        return (R) this;
    }

    /**
     * 设置参数
     */
    public R params(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }

    public R params(String key, String value) {
        params.put(key, value);
        return (R) this;
    }

    public R removeParam(String key) {
        params.remove(key);
        return (R) this;
    }

    public R removeAllParams() {
        params.clear();
        return (R) this;
    }

    /**
     * 根据当前的请求参数，生成对应的OkClient
     */
    private OkHttpClient.Builder generateOkClient() {
        if(readTimeOut <= 0 && writeTimeOut <= 0 && connectTimeOut <= 0 && headers.isEmpty()) {
            OkHttpClient.Builder builder = KidooApiManager.getOkHttpClientBuilder();
            for(Interceptor interceptor : builder.interceptors()) {

            }
            return builder;
        } else {
            OkHttpClient.Builder newClientBuilder = KidooApiManager.getOkHttpClientBuilder();
            if(readTimeOut > 0) {
                newClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            }
            if(writeTimeOut > 0) {
                newClientBuilder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
            }
            if(connectTimeOut > 0) {
                newClientBuilder.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS);
            }
            newClientBuilder.addInterceptor(new HeadersIntercptor(headers));
            for(Interceptor interceptor : interceptors) {
                newClientBuilder.addInterceptor(interceptor);
            }

            if(networkInterceptors.size() > 0) {
                for(Interceptor interceptor : interceptors) {
                    newClientBuilder.addNetworkInterceptor(interceptor);
                }
            }

            return newClientBuilder;
        }
    }

    /**
     * 根据当前的请求参数，生成对应的Retrofit
     */
    private Retrofit.Builder generateRetrofit() {
        if(converterFactories.isEmpty() && adapterFactories.isEmpty()) {
            return KidooApiManager.getRetrofitBuilder().baseUrl(baseUrl);
        } else {
            final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            if(!converterFactories.isEmpty()) {
                for(Converter.Factory conFactory: converterFactories) {
                    retrofitBuilder.addConverterFactory(conFactory);
                }
            } else {
                //获取全局的对象重新设置
                List<Converter.Factory> listConvertFactory = KidooApiManager.getRetrofit().converterFactories();
                for(Converter.Factory conFactory: listConvertFactory) {
                    retrofitBuilder.addConverterFactory(conFactory);
                }
            }

            if(!adapterFactories.isEmpty()) {
                for(CallAdapter.Factory adFactory : adapterFactories) {
                    retrofitBuilder.addCallAdapterFactory(adFactory);
                }
            } else {
                //获取全局的对象重新设置
                List<CallAdapter.Factory> listAdapterFactory = KidooApiManager.getRetrofit().callAdapterFactories();
                for(CallAdapter.Factory adFactory : listAdapterFactory) {
                    retrofitBuilder.addCallAdapterFactory(adFactory);
                }
            }

            return retrofitBuilder;
        }

    }

    protected R build() {
        OkHttpClient.Builder okClientBuilder = generateOkClient();
        final Retrofit.Builder reBuilder = generateRetrofit();
        reBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        okHttpClient = okClientBuilder.build();
        reBuilder.client(okHttpClient);
        retrofit = reBuilder.build();
        apiManager = retrofit.create(ApiService.class);

        return (R)this;
    }

    protected abstract Observable<ResponseBody> generateRequest();
}
