package com.kidoo.customer.mvp.presenter.account;

import android.content.Context;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.R;
import com.kidoo.customer.api.token.AuthModel;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.LoginResult;
import com.kidoo.customer.mvp.contract.LoginContract;
import com.kidoo.customer.mvp.contract.SigninContract;
import com.kidoo.customer.mvp.interactor.account.LoginInteractor;
import com.kidoo.customer.mvp.interactor.account.SignInInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import java.util.Date;

import javax.inject.Inject;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/11/25  
 * update: 2017/11/25
 * version: 
*/

public class SigninPresenterImpl extends BasePresenterImpl<SigninContract.View> implements SigninContract.Presenter  {


    @Inject
    public SignInInteractor mInteractor;

    @Inject
    public LoginInteractor mLoginInractor;

    @Inject
    public SigninPresenterImpl() {
        
    }

    @Override
    public void getSMS(String phoneNumber) {
        mInteractor.getSMSAction(phoneNumber, new SigninContract.Interactor.GetSmsCallback() {
            @Override
            public void onSuccess() {
                mPresenterView.stopSMSCounter(true);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.stopSMSCounter(false);
                mPresenterView.showToast(msg);
            }
        });
    }

    @Override
    public void signin(final Context context, final String phoneNumber, final String pwd, final String captcha) {
        mInteractor.signInAction(context, phoneNumber, pwd, captcha, new SigninContract.Interactor.SignInCallbck() {
            @Override
            public void onSuccess() {
                mPresenterView.showToast(context.getResources().getString(R.string.register_success_hint));
                loginAction(context, phoneNumber, pwd);

            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showToast(msg);
            }
        });
    }


    private void loginAction(final Context context, String account, String pwd) {
        mLoginInractor.doLoginNoNewKeypair(context, account, pwd, new LoginContract.Interactor.LoginCallback() {
            @Override
            public void onSuccess(LoginResult result) {
                AuthModel authModel = new AuthModel();
                long nowTime = new Date().getTime();
                authModel.setGetTokenTime(nowTime);
                authModel.setDifTime(nowTime - result.getServerTime());
                authModel.setServerTime(result.getServerTime());
                authModel.setTokenId(result.getTokenId());
                authModel.setImPasswd(result.getCustomer().getImPassword());
                TokenManager.getInstance().updateAuthModel(TokenManager.KEY_AUTH, authModel);
                AccountHelper.login(result.getCustomer());
                mPresenterView.goMain();
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showToast(context.getResources().getString(R.string.login_faild) + ":" + msg);
            }
        });
    }
}
