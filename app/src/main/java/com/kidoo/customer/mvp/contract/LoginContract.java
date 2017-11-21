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

        void goMainPage();
    }

    interface Presenter extends BasePresenter<View> {

        void loginAction(String account, String pwd);

    }

}
