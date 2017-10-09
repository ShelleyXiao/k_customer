package com.kidoo.customer.api.http.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * User: ShaudXiao
 * Date: 2017-10-09
 * Time: 10:50
 * Company: zx
 * Description: CookieManger 管理器
 * FIXME
 */


public class CookieManger implements CookieJar {



    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return null;
    }
}
