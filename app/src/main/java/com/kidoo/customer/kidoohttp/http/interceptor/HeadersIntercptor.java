package com.kidoo.customer.kidoohttp.http.interceptor;


import com.kidoo.customer.kidoohttp.http.model.HttpHeaders;
import com.kidoo.customer.kidoohttp.http.utils.HttpLogger;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: ShaudXiao
 * Date: 2017-11-20
 * Time: 16:36
 * Company: zx
 * Description: 添加请求头参数
 * FIXME
 */


public class HeadersIntercptor implements Interceptor {

    private HttpHeaders mHeaders;

    public HeadersIntercptor(HttpHeaders headers) {
        mHeaders = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        if(mHeaders.headersMap.isEmpty()) return chain.proceed(builder.build());
        try {
            for(Map.Entry<String, String> entry : mHeaders.headersMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue()).build();
            }
        } catch (Exception e) {
            HttpLogger.e(e);
        }

        return chain.proceed(builder.build());
    }
}
