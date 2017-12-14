package com.kidoo.customer.mvp.contract.user;

import com.kidoo.customer.bean.UserDetailBean;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * description:
 * autour: ShaudXiao
 * date: 2017/12/2
 * update: 2017/12/2
 * version:
*/

public interface UseInfoContract {

    interface View extends BaseView {

        void updateUserInfo(UserDetailBean detail);
    }


    interface Presenter extends BasePresenter<UseInfoContract.View> {

        void queryUserDetail(String mobile);


    }

    interface Interactor {

        Disposable doQueryUserDetail(String phone, UseInfoContract.Interactor.Callback callback);


        interface Callback {
            void onSuccess(UserDetailBean result);
            void onFailure(String msg);
        }
    }
}
