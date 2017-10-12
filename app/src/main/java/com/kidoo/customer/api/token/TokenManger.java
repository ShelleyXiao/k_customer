package com.kidoo.customer.api.token;

import android.text.TextUtils;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.api.ComParamContact;
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

    public AuthModel getAuthModel() {
        if (this.mAuthModel == null
                || this.mAuthModel.getAccessToken() == null
                || this.mAuthModel.getAccessToken().equals("")) {
            Object object = mACache.getAsObject(ComParamContact.Token.AUTH_MODEL);
            if (object != null) {
                this.mAuthModel = (AuthModel) object;
            }
        }

        return this.mAuthModel;
    }

    public void setAuthModel(AuthModel model) {
        if(null != model) {
            this.mAuthModel = model;
            mACache.put(ComParamContact.Token.AUTH_MODEL, this.mAuthModel);
        }
    }

    public void clearAuth() {
        AuthModel auth = new AuthModel();
        auth.setAccessToken("");
        this.mAuthModel = auth;
        mACache.put(ComParamContact.Token.AUTH_MODEL, this.mAuthModel);
        mACache.clear();
    }

    public boolean isTokenVaild() {
        if (getAuthModel() != null && !TextUtils.isEmpty(getAuthModel().getAccessToken())) {
            return true;
        }
        return false;
    }

}
