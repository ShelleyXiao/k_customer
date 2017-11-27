package com.kidoo.customer.mvp.contract;

import android.content.Context;

import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

/**
 * Created by Shelley on 2017/11/25.
 */

public interface SigninContract {

    interface View extends BaseView {

//        void goLogin();

        void stopSMSCounter(boolean smsSucess);
    }

    interface Presenter extends BasePresenter<SigninContract.View> {

        void getSMS(String phoneNumber);

        void signin(Context context, String phoneNumber, String pwd, String captcha);


    }

    interface Interactor {

        void getSMSCall(String phoneNumber, SigninContract.Interactor.GetSmsCallback callback);

        interface GetSmsCallback {
            void onSuccess();
            void onFailure(String msg);
        }

        interface SignInCallbck {
            void onSuccess();
            void onFailure(String msg);
        }
    }


}
