package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.bean.UserDetailBean;
import com.kidoo.customer.mvp.contract.UseInfoContract;
import com.kidoo.customer.mvp.interactor.UserInfoInteractor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/** 
 * description: 
 * autour: ShaudXiao
 * date: 2017/12/2  
 * update: 2017/12/2
 * version: 
*/

public class UserInfoPresenterImpl extends BasePresenterImpl<UseInfoContract.View> implements UseInfoContract.Presenter {

    @Inject
    UserInfoInteractor mInteractor;

    @Inject
    public UserInfoPresenterImpl() {

    }


    @Override
    public void queryUserDetail(String mobile) {
        Disposable disposable = mInteractor.doQueryUserDetail(mobile, new UseInfoContract.Interactor.Callback() {
            @Override
            public void onSuccess(UserDetailBean result) {
                if(result != null) {
                    mPresenterView.updateUserInfo(result);
                }
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showToast(msg);
            }
        });

        addDisposable(disposable);
    }
}
