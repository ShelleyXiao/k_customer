package com.kidoo.customer.mvp.interactor.team;

import com.kidoo.customer.api.QueryTeamDetailApi;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.bean.TeamDetailResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.team.TeamDetailContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ShaudXiao on 2017/12/18.
 */

public class TeamDetailInteractor implements TeamDetailContract.Interactor {

    @Inject
    public TeamDetailInteractor() {

    }

    @Override
    public Disposable queryTeamListAction(String name, final GetTeamDetailCallback callback) {
        Observable<KidooApiResult<TeamDetailResult>> observable = QueryTeamDetailApi.queryDetailAction(name);
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<TeamDetailResult>>() {
            @Override
            public void accept(KidooApiResult<TeamDetailResult> teamBeanKidooApiResult) throws Exception {
                if(teamBeanKidooApiResult.isSuccess()) {
                    callback.onSuccess(teamBeanKidooApiResult.getData());
                } else {
                    callback.onFailure(teamBeanKidooApiResult.getErrorMsg());
                    LogUtils.e(teamBeanKidooApiResult.getErrorMsgDev());
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
