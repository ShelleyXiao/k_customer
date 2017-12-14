package com.kidoo.customer.mvp.interactor.account;

import android.app.Dialog;
import android.content.Context;

import com.kidoo.customer.api.GetSMSApi;
import com.kidoo.customer.api.GetTempRSAKeyPairApi;
import com.kidoo.customer.api.RegisterApi;
import com.kidoo.customer.api.token.RSAKey;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.KeypairResult;
import com.kidoo.customer.bean.RegisterResultBean;
import com.kidoo.customer.bean.SMSCodeBean;
import com.kidoo.customer.cipher.rsa.Base64Utils;
import com.kidoo.customer.cipher.rsa.RSAUtil;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.exception.ApiException;
import com.kidoo.customer.kidoohttp.http.subsciber.IProgressDialog;
import com.kidoo.customer.kidoohttp.http.subsciber.ProgressSubscriber;
import com.kidoo.customer.mvp.contract.SigninContract;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.EncryptUtils;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/11/26  
 * update: 2017/11/26
 * version: 
*/

public class SignInInteractor {

    @Inject
    public SignInInteractor() {}

    public void getSMSAction(final String phone, final SigninContract.Interactor.GetSmsCallback callback) {
        Observable<KidooApiResult<KeypairResult>> keyObservable = GetTempRSAKeyPairApi.getTempRSAKeyPair(phone);
        keyObservable.flatMap(new Function<KidooApiResult<KeypairResult>, ObservableSource<KidooApiResult<SMSCodeBean>>>() {
            @Override
            public ObservableSource<KidooApiResult<SMSCodeBean>> apply(KidooApiResult<KeypairResult> keypairResultKidooApiResult) throws Exception {
                if(keypairResultKidooApiResult.isSuccess()) {
                    RSAKey key = new RSAKey();
                    key.setPrivateKey(keypairResultKidooApiResult.getData().getPrivateKey());
                    key.setPublickKey(keypairResultKidooApiResult.getData().getPublicKey());
                    TokenManager.getInstance().updateRSAKey(TokenManager.KEY_RSA, key);
                    LogUtils.d(TokenManager.getInstance().getRSAKey(TokenManager.KEY_RSA));
                    LogUtils.d(phone);
                    byte[] encryptPhoneByte = RSAUtil.encryptByPublicKey(phone, keypairResultKidooApiResult.getData().getPublicKey());

                    String encryptPhone = Base64Utils.encode(encryptPhoneByte);

//                    LogUtils.d(encryptPhone);
                    return GetSMSApi.getSMS(phone, "1", encryptPhone);
                } else {
                    return null;
                }
            }
        }).subscribe(new Consumer<KidooApiResult<SMSCodeBean>>() {
            @Override
            public void accept(KidooApiResult<SMSCodeBean> smsCodeBeanKidooApiResult) throws Exception {
                if(smsCodeBeanKidooApiResult.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure(smsCodeBeanKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void signInAction(final Context context,  String phoneNumber, String pwd, String captcha, final SigninContract.Interactor.SignInCallbck callback) {
        RSAKey rsaKey = TokenManager.getInstance().getRSAKey(TokenManager.KEY_RSA);
        String publicKey  = rsaKey.getPublickKey();
        try {
            String sha1Pwd = EncryptUtils.encryptSHA1ToString(pwd).toLowerCase();
            byte[] b_rsapwd = RSAUtil.encryptByPublicKey(sha1Pwd, publicKey);
            String finalPwd = Base64Utils.encode(b_rsapwd);
            Observable<KidooApiResult<RegisterResultBean>> observable = RegisterApi.register(phoneNumber, finalPwd, captcha);
            observable.subscribe(new ProgressSubscriber<KidooApiResult<RegisterResultBean>>(context, new IProgressDialog() {
                @Override
                public Dialog getDialog() {
                    return DialogHelper.getLoadingDialog(context);
                }
            }) {
                @Override
                public void onNext(KidooApiResult<RegisterResultBean> registerResultBeanKidooApiResult) {
                    LogUtils.i("login success");
                    if(registerResultBeanKidooApiResult.isSuccess()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(registerResultBeanKidooApiResult.getErrorMsg());
                        dismissProgress();
                    }
                }

                @Override
                public void onError(ApiException e) {
                    super.onError(e);
                    callback.onFailure(e.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
