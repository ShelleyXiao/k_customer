package com.kidoo.customer.api.http.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kidoo.customer.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * User: ShaudXiao
 * Date: 2017-10-09
 * Time: 10:57
 * Company: zx
 * Description: Cookie 存储器
 * FIXME
 */


public class PersistantCookie {

    private static final String COOKIE_KEY = "Cookie_prefs";

    private SharedPreferences cookiPreferences;
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;

    public PersistantCookie(Context context) {
        cookiPreferences = context.getSharedPreferences(COOKIE_KEY, 0);
        cookies = new HashMap<>();
        Map<String, ?> prefsMap = cookiPreferences.getAll();
        for(Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            String[] cookieName = TextUtils.split((String) entry.getValue(), ",");
            for(String name : cookieName) {
                String encodedCookie = cookiPreferences.getString(name, null);
                if(null != encodedCookie) {
                    Cookie decodedCookie = decodedCookie(encodedCookie);
                    if(decodedCookie != null) {
                        if(!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                        }
                    }   cookies.get(entry.getKey()).put(name, decodedCookie);
                }
            }

        }
    }

    protected String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);
        if (cookie.persistent()) {
            if (!cookies.containsKey(url.host())) {
                cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
            }
            cookies.get(url.host()).put(name, cookie);
            SharedPreferences.Editor prefsWriter = cookiPreferences.edit();
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            prefsWriter.putString(name, encodeCookie(new SerializableOkHttpCookies(cookie)));
            prefsWriter.apply();
        } else {
            if (cookies.containsKey(url.host())) {
                cookies.get(url.host()).remove(name);
            }
        }
    }

    public void addCookies(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            String domain = cookie.domain();
            ConcurrentHashMap<String, Cookie> domainCookies = this.cookies.get(domain);
            if (domainCookies == null) {
                domainCookies = new ConcurrentHashMap<>();
                this.cookies.put(domain, domainCookies);
            }
            cookies.add(cookie);
        }
    }

    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host()))
            ret.addAll(cookies.get(url.host()).values());
        return ret;
    }

    public boolean removeAll() {
        SharedPreferences.Editor prefsWriter = cookiPreferences.edit();
        prefsWriter.clear();
        prefsWriter.apply();
        cookies.clear();
        return true;
    }

    public boolean remove(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);

        if (cookies.containsKey(url.host()) && cookies.get(url.host()).containsKey(name)) {
            cookies.get(url.host()).remove(name);

            SharedPreferences.Editor prefsWriter = cookiPreferences.edit();
            if (cookiPreferences.contains(name)) {
                prefsWriter.remove(name);
            }
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            prefsWriter.apply();

            return true;
        } else {
            return false;
        }
    }

    public List<Cookie> getCookies() {
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet())
            ret.addAll(cookies.get(key).values());

        return ret;
    }

    protected String encodeCookie(SerializableOkHttpCookies cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            LogUtils.w("IOException in encodeCookie" + e.getMessage());
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    protected Cookie decodedCookie(String  cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.w("IOException in decodeCookie");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtils.w("ClassNotFoundException in decodeCookie");
        }
        return cookie;
    }

    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for(int i = 0; i < len; i+=2) {
            data[i / 2] = (byte)((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }

        return data;
    }

}
