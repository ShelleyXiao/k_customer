package com.kidoo.customer.mvp.presenter;

import android.content.Context;

import com.kidoo.customer.bean.LoginResult;
import com.kidoo.customer.mvp.contract.LoginContract;
import com.kidoo.customer.mvp.contract.SigninContract;
import com.kidoo.customer.mvp.interactor.LoginInteractor;
import com.kidoo.customer.mvp.interactor.SignInInteractor;

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
            }
        });
    }

    @Override
    public void signin(final Context context, final String phoneNumber, final String pwd, final String captcha) {
        mInteractor.signInAction(context, phoneNumber, pwd, captcha, new SigninContract.Interactor.SignInCallbck() {
            @Override
            public void onSuccess() {
                loginAction(context, phoneNumber, pwd);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showToast(msg);
            }
        });
    }


    private void loginAction(Context context, String account, String pwd) {
        mLoginInractor.doLogin(context, account, pwd, new LoginContract.Interactor.LoginCallback() {
            @Override
            public void onSuccess(LoginResult result) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
