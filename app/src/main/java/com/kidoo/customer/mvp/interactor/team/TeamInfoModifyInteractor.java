package com.kidoo.customer.mvp.interactor.team;

import com.kidoo.customer.api.ModifyTeamInfoApi;
import com.kidoo.customer.api.QueryPicInfoApi;
import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.team.TeamInfoModifyContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-19
 * Time: 19:10
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamInfoModifyInteractor implements TeamInfoModifyContract.Interactor {

    @Inject
    public TeamInfoModifyInteractor() {

    }

    @Override
    public Disposable updateTeamInfoAction(String teamId, String teamName, String teamMsg, String teamLogo, final UpdateTeamInfoCallbck callbck) {
        Observable<KidooApiResult<ReturnNullBean>> observable = ModifyTeamInfoApi.modifyTeamInfo(teamId, teamName, teamMsg, teamMsg);
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<ReturnNullBean>>() {
            @Override
            public void accept(KidooApiResult<ReturnNullBean> result) throws Exception {
                if(result.isSuccess()) {
                    callbck.onSuccess();
                } else {
                    callbck.onFailure(result.getErrorMsg());
                    LogUtils.e(result.getErrorMsgDev());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callbck.onFailure(throwable.getMessage());
                LogUtils.e(throwable.getMessage());
            }
        });

        return null;
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
