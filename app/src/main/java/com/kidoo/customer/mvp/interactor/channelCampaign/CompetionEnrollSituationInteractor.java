package com.kidoo.customer.mvp.interactor.channelCampaign;

import com.kidoo.customer.api.QueryCompetionEnrollApi;
import com.kidoo.customer.api.QueryTeamsBaseInfoApi;
import com.kidoo.customer.bean.EnrollSituationResult;
import com.kidoo.customer.bean.TeamsBaseInfoResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.channelCampaign.CompetionEnrollContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 14:15
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionEnrollSituationInteractor implements CompetionEnrollContract.Interactor {

    @Inject
    public CompetionEnrollSituationInteractor() {
    }

    @Override
    public Disposable queryCompetionEnrollAction(int matchID, final GetCompetionEnrollCallback callback) {
        Observable<KidooApiResult<EnrollSituationResult>> observable = QueryCompetionEnrollApi.queryCompetionEnroll(matchID + "");
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<EnrollSituationResult>>() {
            @Override
            public void accept(KidooApiResult<EnrollSituationResult> result) throws Exception {
                if (result.isSuccess()) {
                    callback.onSuccess(result.getData());
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
    public Disposable queryTeamsBaesInfoAction(String teamIds, final GetTeamBaseInfoCallback callback) {
        Observable<KidooApiResult<TeamsBaseInfoResult>> observable = QueryTeamsBaseInfoApi.queryUserBaseInfo(teamIds);
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<TeamsBaseInfoResult>>() {
            @Override
            public void accept(KidooApiResult<TeamsBaseInfoResult> result) throws Exception {
                if (result.isSuccess()) {
                    callback.onSuccess(result.getData().getTeamList());
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
