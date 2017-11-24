package com.kidoo.customer.mvp.interactor;

import com.kidoo.customer.mvp.contract.LoginContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

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
    public void doLogin(String account, String pwd, LoginCallback callback) {
        LogUtils.i("interactor login");
    }
}
