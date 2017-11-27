package com.kidoo.customer.mvp.contract;

import android.content.Context;

import com.kidoo.customer.bean.LoginResult;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 10:15
 * Company: zx
 * Description: 登陆接口协议
 * FIXME
 */


public interface LoginContract {

    interface View extends BaseView {

        void goMainPage();
    }

    interface Presenter extends BasePresenter<View> {

        void loginAction(Context context, String account, String pwd);

    }

    interface Interactor {

        void doLogin(Context context, String account, String pwd, LoginCallback callback);

        interface LoginCallback {
            void onSuccess(LoginResult result);
            void onFailure(String msg);
        }
    }

}
