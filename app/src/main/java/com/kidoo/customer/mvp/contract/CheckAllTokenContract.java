package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.bean.CheckAllTokenBean;
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


public interface CheckAllTokenContract {

    interface View extends BaseView {

        void goMain();

        void goLogin();
    }

    interface Presenter extends BasePresenter<View> {

        void checkAllTokenAction();

    }

    interface Interactor {


        void doCheckAllToken(String customId, String tokenId, String token, CheckAllTokenContract.Interactor.CheckAllTokenCallback callback);

        interface CheckAllTokenCallback {
            void onSuccess(CheckAllTokenBean result);
            void onFailure(String msg);
        }


    }

}
