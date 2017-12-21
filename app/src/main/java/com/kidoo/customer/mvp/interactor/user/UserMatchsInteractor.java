package com.kidoo.customer.mvp.interactor.user;

import com.kidoo.customer.api.QueryMyMatchsApi;
import com.kidoo.customer.bean.MyMatchsResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.user.UserMatchsContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-21
 * Time: 15:39
 * Company: zx
 * Description:
 * FIXME
 */


public class UserMatchsInteractor implements UserMatchsContract.Interactor {

    @Inject
    public UserMatchsInteractor() {

    }

    @Override
    public Disposable queryMoreMatchsAction(long customerId2Query, int pageSize, int pageNo, final GetMatchsCallback callback) {
        Observable<KidooApiResult<MyMatchsResult>> observable = QueryMyMatchsApi.queryMatchS(String.valueOf(customerId2Query)
                , String.valueOf(pageNo), String.valueOf(pageSize));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<MyMatchsResult>>() {
            @Override
            public void accept(KidooApiResult<MyMatchsResult> result) throws Exception {
                if (result.isSuccess()) {
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(result.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        return disposable;
    }

    @Override
    public Disposable queryMacths(long customerId2Query, final GetMatchsCallback callback) {
        Observable<KidooApiResult<MyMatchsResult>> observable = QueryMyMatchsApi.queryMatchS(String.valueOf(customerId2Query));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<MyMatchsResult>>() {
            @Override
            public void accept(KidooApiResult<MyMatchsResult> matchListResultKidooApiResult) throws Exception {

                if (matchListResultKidooApiResult.isSuccess()) {
                    callback.onSuccess(matchListResultKidooApiResult);
                } else {
                    callback.onFailure(matchListResultKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        return disposable;
    }
}
