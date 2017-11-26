package com.kidoo.customer.mvp.presenter;

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
    public void signin(final String phoneNumber, final String pwd, final String captcha) {
        mInteractor.signInAction(phoneNumber, pwd, captcha, new SigninContract.Interactor.SignInCallbck() {
            @Override
            public void onSuccess() {
                loginAction(phoneNumber, pwd);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    private void loginAction(String account, String pwd) {
        mLoginInractor.doLogin(account, pwd, new LoginContract.Interactor.LoginCallback() {
            @Override
            public void onSuccess(LoginResult result) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
