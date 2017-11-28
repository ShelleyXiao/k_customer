package com.kidoo.customer.api.token;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.cache.ACache;

/**
 * User: ShaudXiao
 * Date: 2017-10-12
 * Time: 10:44
 * Company: zx
 * Description: Token管理
 * FIXME
 */


public class TokenManager {

    public static final String KEY_AUTH = "auth_model_key";
    public static final String KEY_RSA = "RSA_key";

    private static TokenManager instance = null;
    private ACache mACache;
    private AuthModel mAuthModel;
    private RSAKey mRSAKey;


    private Long timestamep = System.currentTimeMillis();

    public TokenManager() {
        mACache = ACache.get(AppContext.context(), KEY_AUTH);
        this.mAuthModel = new AuthModel();
        this.mRSAKey = new RSAKey();
    }

    public static TokenManager getInstance() {
        if (null == instance) {
            synchronized (TokenManager.class) {
                if (null == instance) {
                    instance = new TokenManager();
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

    public RSAKey getRSAKey(String key) {
        Object object = mACache.getAsObject(key);
        RSAKey rsaKey = new RSAKey();
        if (object != null) {
            rsaKey = (RSAKey) object;
        }

        return rsaKey;
    }

    public void setAuthModel(String key, AuthModel model) {
        if (null != model) {
            mACache.put(key, model);
        }
    }

    public void updateAuthModel(String key , AuthModel model) {
        clearAuth(key);
        setAuthModel(key, model);
    }

    public void setRSAKey(String key, RSAKey model) {
        if (null != model) {
            mACache.put(key, model);
        }
    }

    public void updateRSAKey(String key , RSAKey model) {
        clearRSAKey(key);
        setRSAKey(key, model);
    }

    public void clearAuth(String key) {
        AuthModel auth = new AuthModel();
        this.mAuthModel = auth;
        mACache.put(key, this.mAuthModel);
        mACache.remove(key);
    }

    public void clearRSAKey(String key) {
        RSAKey rsaKey = new RSAKey();
        this.mRSAKey = rsaKey;
        mACache.put(key, this.mRSAKey);
        mACache.remove(key);
    }

}
