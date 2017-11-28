package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

/**
 * User: ShaudXiao
 * Date: 2017-11-28
 * Time: 11:03
 * Company: zx
 * Description:
 * FIXME
 */


public interface InitDataContract {

    interface View extends BaseView {


    }

    interface Presenter extends BasePresenter<InitDataContract.View> {

        void getInitData();


    }

    interface Interactor {

        void doGetInitData(String version, String appType, InitDataCallback callback);

        interface InitDataCallback {
            void onSuccess(InitData result);
            void onFailure(String msg);
        }
    }
}
