package com.kidoo.customer.mvp.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.AuthModel;
import com.kidoo.customer.bean.Customer;
import com.kidoo.customer.bean.KeypairResult;
import com.kidoo.customer.bean.LoginResult;
import com.kidoo.customer.cipher.rsa.Base64Utils;
import com.kidoo.customer.cipher.rsa.RSAUtil;
import com.kidoo.customer.kidoohttp.api.ComParamContact;
import com.kidoo.customer.mvp.contract.LoginContract;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.EncryptUtils;
import com.kidoo.customer.utils.LogUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 11:04
 * Company: zx
 * Description:
 * FIXME
 */

public class LoginPresenterImpl extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.View view;
    private KeypairResult mTempKeypairResult;

    private IProgressDialog mDialog;

    @Inject
    public LoginPresenterImpl() {
        mDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                return  DialogHelper.getLoadingDialog((Activity)view);
            }
        };
    }

//    public LoginPresenterImpl(final LoginContract.View view) {
//        mDialog = new IProgressDialog() {
//            @Override
//            public Dialog getDialog() {
//                return  DialogHelper.getLoadingDialog((Activity)view);
//            }
//        };
//    }
//
//    @Override
//    public void refreshTempToken(String account) {
//        LogUtils.i("refreshTempToken");
//        EasyHttp.post(ComParamContact.TempKey.PATH)
//                .params("mobile", account)
//                .execute(new SimpleCallBack<KeypairResult>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        LogUtils.w("Error: " + e.getMessage());
//                        view.refreshTempKeyNotify(false, e.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccess(KeypairResult keypairResult) {
//                        mTempKeypairResult = keypairResult;
//                        LogUtils.w(mTempKeypairResult.toString());
//                        view.refreshTempKeyNotify(true, null);
//                    }
//                });
//    }

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

                EasyHttp.post(ComParamContact.Login.PATH)
                        .params(ComParamContact.Login.ACCOUNT, account)
                        .params(ComParamContact.Login.PASSWORD, (finalPwd))
                        .params(ComParamContact.Login.LOGINTYPE, "1")
                        .execute(new ProgressDialogCallBack<LoginResult>(mDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
//                                view.showNetworkError(e.getMessage());
                            }

                            @Override
                            public void onSuccess(LoginResult result) {
                                if (result != null) {
                                    Customer customer = result.getCustomer();
                                    AuthModel authModel = new AuthModel();
                                    authModel.setAccessToken(result.getTokenId());
                                    authModel.setAccessTokenExpires(String.valueOf(result.getServerTime()));

//                                    LogUtils.w(customer);

                                    if (AccountHelper.login(customer)) {
                                        TokenManager.getInstance().setAuthModel(ComParamContact.Token.AUTH_MODEL, authModel);
                                    } else {
//                                        view.showNetworkError(((Activity)view).getString(R.string.login_exception_hint));
                                    }
                                } else {

                                }

                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
