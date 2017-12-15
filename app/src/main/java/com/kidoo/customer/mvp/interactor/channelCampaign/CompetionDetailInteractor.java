package com.kidoo.customer.mvp.interactor.channelCampaign;

import com.kidoo.customer.api.QueryMatchDetailApi;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.channelCampaign.CompetionDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 15:07
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionDetailInteractor implements CompetionDetailContract.Interactor {

    @Inject
    public CompetionDetailInteractor() {

    }

    @Override
    public Disposable queryCompetionDetailAction(int matchID, final GetCompetionDetailCallback callback) {

        Observable<KidooApiResult<CompetionDetailResult>> observable = QueryMatchDetailApi.queryMatchDetail(String.valueOf(matchID));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<CompetionDetailResult>>() {
            @Override
            public void accept(KidooApiResult<CompetionDetailResult> competionDetailResultKidooApiResult) throws Exception {
                if (competionDetailResultKidooApiResult.isSuccess()) {
                    callback.onSuccess(competionDetailResultKidooApiResult.getData());
                } else {
                    callback.onFailure(competionDetailResultKidooApiResult.getErrorMsg());
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
