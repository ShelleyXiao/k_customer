package com.kidoo.customer.mvp.interactor;

import com.kidoo.customer.api.QueryPicInfoApi;
import com.kidoo.customer.api.UpdateUserInfoApi;
import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.UseDetailContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-04
 * Time: 18:25
 * Company: zx
 * Description:
 * FIXME
 */


public class UserDetailInteractor implements UseDetailContract.Interactor {

    @Inject
    public UserDetailInteractor() {

    }

    @Override
    public Disposable updateUserInfoAction(String token, String customerId, String realName, String nickName, String email, String portait, String sex, String brithday, String sign, final UpdateInfoCallbck callbck) {
        Observable<KidooApiResult<ReturnNullBean>> observable = UpdateUserInfoApi.updateUserInfoApi(
                customerId, realName, nickName, email, portait, sex, brithday, sign
        );

        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<ReturnNullBean>>() {
            @Override
            public void accept(KidooApiResult<ReturnNullBean> returnNullBeanKidooApiResult) throws Exception {
                LogUtils.i(returnNullBeanKidooApiResult.toString());
                if (returnNullBeanKidooApiResult.isSuccess()) {
                    callbck.onSuccess();
                } else {
                    callbck.onFailure(returnNullBeanKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callbck.onFailure(throwable.getMessage());
            }
        });

        return disposable;

    }

    @Override
    public Disposable queryPicInfoAction(final GetTokenCallback callback) {

        Observable<KidooApiResult<QNToken>> observable = QueryPicInfoApi.queryPIcInfo();
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<QNToken>>() {
            @Override
            public void accept(KidooApiResult<QNToken> qnTokenKidooApiResult) throws Exception {
                if (qnTokenKidooApiResult.isSuccess()) {
                    callback.onSuccess(qnTokenKidooApiResult.getData());
                } else {
                    callback.onFailure(qnTokenKidooApiResult.getErrorMsg());
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
