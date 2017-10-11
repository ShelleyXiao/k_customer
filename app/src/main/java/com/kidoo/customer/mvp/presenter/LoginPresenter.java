package com.kidoo.customer.mvp.presenter;

import android.text.TextUtils;

import com.kidoo.customer.api.ComParamContact;
import com.kidoo.customer.api.http.HttpManager;
import com.kidoo.customer.api.http.callback.SimpleCallBack;
import com.kidoo.customer.api.http.exception.ApiException;
import com.kidoo.customer.cipher.rsa.Base64Utils;
import com.kidoo.customer.cipher.rsa.RSAUtil;
import com.kidoo.customer.mvp.contract.LoginContract;
import com.kidoo.customer.mvp.model.KeypairResult;
import com.kidoo.customer.mvp.model.LoginResult;
import com.kidoo.customer.utils.EncryptUtils;
import com.kidoo.customer.utils.LogUtils;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 11:04
 * Company: zx
 * Description:
 * FIXME
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private KeypairResult mTempKeypairResult;

    public LoginPresenter(LoginContract.View view) {
        attachView(view);
    }

    @Override
    public void refreshTempToken(String account) {
        HttpManager.post(ComParamContact.TempKey.PATH)
                .params("mobile", account)
                .execute(new SimpleCallBack<KeypairResult>() {
                    @Override
                    public void onError(ApiException e) {
                        LogUtils.w("Error: " + e.getMessage());
                        view.refreshTempKeyNotify(false, e.getMessage());
                    }

                    @Override
                    public void onSuccess(KeypairResult keypairResult) {
                        mTempKeypairResult = keypairResult;
                        LogUtils.w(mTempKeypairResult.toString());
                        view.refreshTempKeyNotify(true, null);
                    }
                });
    }

    @Override
    public void loginAction(String account, String pwd) {
        LogUtils.w(account + " " + pwd);
        if (null != mTempKeypairResult && !TextUtils.isEmpty(mTempKeypairResult.getPublicKey())) {
            String sha1Pwd = EncryptUtils.encryptSHA1ToString(pwd).toLowerCase();
            LogUtils.w(sha1Pwd);
            try {
                LogUtils.w(mTempKeypairResult.getPublicKey());

                byte[] b_rsapwd = RSAUtil.encryptByPublicKey(sha1Pwd, mTempKeypairResult.getPublicKey());
                String finalPwd = Base64Utils.encode(b_rsapwd);

                HttpManager.post(ComParamContact.Login.PATH)
                        .params(ComParamContact.Login.ACCOUNT, account)
                        .params(ComParamContact.Login.PASSWORD, (finalPwd))
                        .params(ComParamContact.Login.LOGINTYPE, "1")
                        .execute(new SimpleCallBack<LoginResult>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtils.w("Error: " + e.getMessage());
                                view.showToast(e.getMessage());
                            }

                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                LogUtils.w(loginResult.toString());
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
