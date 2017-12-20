package com.kidoo.customer.mvp.interactor.channelCampaign;

import com.kidoo.customer.api.EnrollMatchApi;
import com.kidoo.customer.api.QuitMatchApi;
import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.channelCampaign.CompetionEnrollActionContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 11:52
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionEnrollActionInteractor implements CompetionEnrollActionContract.Interactor {

    @Inject
    public CompetionEnrollActionInteractor() {

    }

    @Override
    public Disposable quitCompetionEnrollAction(int matchId, final CompetionEnrollActionCallback callback) {
        Observable<KidooApiResult<ReturnNullBean>> observable = QuitMatchApi.quitMatch(matchId + "");
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<ReturnNullBean>>() {
            @Override
            public void accept(KidooApiResult<ReturnNullBean> result) throws Exception {
                if(result.isSuccess()) {
                    callback.onSuccess();

                } else {
                    callback.onFailure(result.getErrorMsg());
                    LogUtils.e(result.getErrorMsgDev());
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

    @Override
    public Disposable competionEnrollAction(int matchId, final CompetionEnrollActionCallback callback) {
        Observable<KidooApiResult<ReturnNullBean>> observable = EnrollMatchApi.enrollMatch(matchId + "");
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<ReturnNullBean>>() {
            @Override
            public void accept(KidooApiResult<ReturnNullBean> result) throws Exception {
                if(result.isSuccess()) {
                    callback.onSuccess();

                } else {
                    callback.onFailure(result.getErrorMsg());
                    LogUtils.e(result.getErrorMsgDev());
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
