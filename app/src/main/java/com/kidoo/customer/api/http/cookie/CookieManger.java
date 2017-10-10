package com.kidoo.customer.api.http.cookie;

import android.content.Context;

import java.util.ArrayList;
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


    private static Context mContext;
    private static PersistantCookie cookieStore;

    public CookieManger(Context context) {
        mContext = context;
        if (cookieStore == null) {
            cookieStore = new PersistantCookie(mContext);
        }
    }

    public void addCookies(List<Cookie> cookies) {
        cookieStore.addCookies(cookies);
    }

    public void saveFromResponse(HttpUrl url, Cookie cookie) {
        if (cookie != null) {
            cookieStore.add(url, cookie);
        }
    }

    public PersistantCookie getCookieStore() {
        return cookieStore;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }


    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    public void remove(HttpUrl url, Cookie cookie) {
        cookieStore.remove(url, cookie);
    }

    public void removeAll() {
        cookieStore.removeAll();
    }
}
