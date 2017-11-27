package com.kidoo.customer.mvp.interactor;

import android.app.Dialog;
import android.content.Context;

import com.kidoo.customer.api.GetTempRSAKeyPairApi;
import com.kidoo.customer.api.LoginApi;
import com.kidoo.customer.bean.KeypairResult;
import com.kidoo.customer.bean.LoginResult;
import com.kidoo.customer.cipher.rsa.Base64Utils;
import com.kidoo.customer.cipher.rsa.RSAUtil;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.subsciber.ProgressSubscriber;
import com.kidoo.customer.mvp.contract.LoginContract;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.EncryptUtils;
import com.kidoo.customer.utils.LogUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * User: ShaudXiao
 * Date: 2017-11-23
 * Time: 09:38
 * Company: zx
 * Description:
 * FIXME
 */


public class LoginInteractor implements LoginContract.Interactor {

    @Inject
    LoginInteractor() {

    }

    @Override
    public void doLogin(final Context context, final String account, final String pwd, final LoginCallback callback) {
        LogUtils.i("interactor login");
        Observable<KidooApiResult<KeypairResult>> keyObservable = GetTempRSAKeyPairApi.getTempRSAKeyPair(account);
        keyObservable.flatMap(new Function<KidooApiResult<KeypairResult>, ObservableSource<KidooApiResult<LoginResult>>>() {
            @Override
            public ObservableSource<KidooApiResult<LoginResult>> apply(KidooApiResult<KeypairResult> keypairResultKidooApiResult) throws Exception {
                if(keypairResultKidooApiResult.isSuccess()) {
                    String publicKey = keypairResultKidooApiResult.getData().getPublicKey();
                    String privateKey = keypairResultKidooApiResult.getData().getPrivateKey();

                    String sha1Pwd = EncryptUtils.encryptSHA1ToString(pwd).toLowerCase();
                    byte[] b_rsapwd = RSAUtil.encryptByPublicKey(sha1Pwd, publicKey);
                    String finalPwd = Base64Utils.encode(b_rsapwd);

                    return LoginApi.login(finalPwd, pwd, "1");
                } else {
                    return  null;
                }
            }
        })
//                .subscribe(new Consumer<KidooApiResult<LoginResult>>() {
//            @Override
//            public void accept(KidooApiResult<LoginResult> loginResultKidooApiResult) throws Exception {
//                LogUtils.i("login success");
//                callback.onSuccess(loginResultKidooApiResult.getData());
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                LogUtils.e(throwable.getMessage());
//                callback.onFailure(throwable.getMessage());
//            }
//        });

        .subscribe(new ProgressSubscriber<KidooApiResult<LoginResult>>(context, new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                return DialogHelper.getLoadingDialog(context);
            }
        }) {
            @Override
            public void onNext(KidooApiResult<LoginResult> loginResultKidooApiResult) {
                LogUtils.i("login success");
                if(loginResultKidooApiResult.isSuccess()) {
                    callback.onSuccess(loginResultKidooApiResult.getData());
                } else {
                    callback.onFailure(loginResultKidooApiResult.getErrorMsg());
                    dismissProgress();
                }
            }

            @Override
            public void onError(ApiException e) {
                super.onError(e);
                callback.onFailure(e.getMessage());
            }
        });
    }
}
