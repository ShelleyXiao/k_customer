package com.kidoo.customer.api.token;

import android.text.TextUtils;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.cache.ACache;
import com.kidoo.customer.mvp.model.AuthModel;

/**
 * User: ShaudXiao
 * Date: 2017-10-12
 * Time: 10:44
 * Company: zx
 * Description: Token管理
 * FIXME
 */


public class TokenManger {

    private final String KEY = "auth_model_key";
    private static TokenManger instance = null;
    private ACache mACache;
    private AuthModel mAuthModel;
    private Long timestamep = System.currentTimeMillis();

    public TokenManger() {
        mACache = ACache.get(AppContext.context(), KEY);
        this.mAuthModel = new AuthModel();
        this.mAuthModel.setAccessToken("");
    }

    public static TokenManger getInstance() {
        if (null == instance) {
            synchronized (TokenManger.class) {
                if (null == instance) {
                    instance = new TokenManger();
                }
            }
        }

        return instance;
    }

    public Long getTimestamep() {
        return timestamep;
    }

    public void setTimestamep(Long timestamep) {
        this.timestamep = timestamep;
    }

    public AuthModel getAuthModel(String key) {
        Object object = mACache.getAsObject(key);
        AuthModel authModel = new AuthModel();
        if (object != null) {
            authModel = (AuthModel) object;
        }


        return authModel;
    }

    public void setAuthModel(String key, AuthModel model) {
        if (null != model) {
            mACache.put(key, model);
        }
    }

    public void clearAuth(String key) {
        AuthModel auth = new AuthModel();
        auth.setAccessToken("");
        this.mAuthModel = auth;
        mACache.put(key, this.mAuthModel);
        mACache.clear();
    }

    public boolean isTokenVaild(String key) {
        if (getAuthModel(key) != null && !TextUtils.isEmpty(getAuthModel(key).getAccessToken())) {
            return true;
        }
        return false;
    }

}
