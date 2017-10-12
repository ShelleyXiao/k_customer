package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 10:15
 * Company: zx
 * Description:
 * FIXME
 */


public interface LoginContract {

    interface View extends BaseView {
        void showToast(String msg);

        void refreshTempKeyNotify(boolean success, String errorMsg);

        void loginResultNotify(boolean success) ;
    }

    interface Presenter extends BasePresenter<View> {

        void refreshTempToken(String account);

        void loginAction(String account, String pwd);


    }

}
