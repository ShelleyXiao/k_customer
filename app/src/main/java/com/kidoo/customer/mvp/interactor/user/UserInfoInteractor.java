package com.kidoo.customer.mvp.interactor.user;

import com.kidoo.customer.api.QueryUserDetailApi;
import com.kidoo.customer.bean.UserDetailBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.user.UseInfoContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Shelley on 2017/12/2.
 */

public class UserInfoInteractor implements UseInfoContract.Interactor {


    @Inject
    public UserInfoInteractor() {};

    @Override
    public Disposable doQueryUserDetail(final String phone, final Callback callback) {
        LogUtils.i(phone);
        Observable<KidooApiResult<UserDetailBean>> observable = QueryUserDetailApi.queryDetailAction(phone);
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<UserDetailBean>>() {
            @Override
            public void accept(KidooApiResult<UserDetailBean> userDetailBeanKidooApiResult) throws Exception {
                if(userDetailBeanKidooApiResult.isSuccess()) {
                    callback.onSuccess(userDetailBeanKidooApiResult.getData());
                } else {
                    callback.onFailure(userDetailBeanKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFailure(throwable.getMessage());
            }
        });

        return disposable;
    }
}
