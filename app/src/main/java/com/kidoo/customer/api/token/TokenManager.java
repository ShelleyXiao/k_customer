package com.kidoo.customer.api.token;

import android.os.SystemClock;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.cache.ACache;
import com.kidoo.customer.cipher.rsa.Base64Utils;
import com.kidoo.customer.cipher.rsa.RSAUtil;

import java.util.Date;

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

    public synchronized AuthModel getAuthModel(String key) {
        Object object = mACache.getAsObject(key);
        AuthModel authModel = new AuthModel();
        if (object != null) {
            authModel = (AuthModel) object;
        }

        return authModel;
    }

    public synchronized RSAKey getRSAKey(String key) {
        Object object = mACache.getAsObject(key);
        RSAKey rsaKey = new RSAKey();
        if (object != null) {
            rsaKey = (RSAKey) object;
        }

        return rsaKey;
    }

    public void setAuthModel(String key, AuthModel model) {
        if (null != model) {
            mAuthModel = model;
            mACache.put(key, model);
        }
    }

    public void updateAuthModel(String key, AuthModel model) {
        clearAuth(key);
        setAuthModel(key, model);
    }

    public void setRSAKey(String key, RSAKey model) {
        if (null != model) {
            this.mRSAKey = model;
            mACache.put(key, model);
        }
    }

    public void updateRSAKey(String key, RSAKey model) {
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

    public String getToken() {
        if (mAuthModel == null) {
            mAuthModel = getAuthModel(TokenManager.KEY_AUTH);
        }

        if (mRSAKey == null) {
            mRSAKey = getRSAKey(TokenManager.KEY_RSA);
        }
        if(mRSAKey == null) {
            int count = 5;

            while ((mRSAKey == null) && count-- > 0) {
                mRSAKey = getRSAKey(TokenManager.KEY_RSA);
                SystemClock.sleep(100);
            }
        }

        long getTokenTime = mAuthModel.getGetTokenTime();
        long difTime = mAuthModel.getDifTime();
        long nowTime = new Date().getTime();
        String serverTime = String.valueOf(nowTime - difTime);
        try {
//            LogUtils.i(mRSAKey.getPublickKey());
            byte[] bytesToken = RSAUtil.encryptByPublicKey(serverTime, mRSAKey.getPublickKey());
            String token = Base64Utils.encode(bytesToken);

            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

}
