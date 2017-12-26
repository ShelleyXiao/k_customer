package com.kidoo.customer.mvp.interactor.user;

import com.kidoo.customer.api.QueryMyTeamListApi;
import com.kidoo.customer.bean.QueryTeamResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.user.UserTeamsContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ShaudXiao on 2017/12/17.
 */

public class UserTeamsInteractor implements UserTeamsContract.Interactor{

    @Inject
    public UserTeamsInteractor() {

    }


    @Override
    public Disposable queryTeamListAction(String constomId, int pageNO, int pageSize, final GetTeamListCallback callback) {
        Observable<KidooApiResult<QueryTeamResult>> observable = QueryMyTeamListApi.queryMyTeam(constomId,
                String.valueOf(pageNO),
                String.valueOf(pageSize));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<QueryTeamResult>>() {
            @Override
            public void accept(KidooApiResult<QueryTeamResult> queryTeamResultKidooApiResult) throws Exception {
                if(queryTeamResultKidooApiResult.isSuccess()) {
                    callback.onSuccess(queryTeamResultKidooApiResult);
                } else {
                    LogUtils.e(queryTeamResultKidooApiResult.getErrorMsgDev());
                    callback.onFailure(queryTeamResultKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e(throwable.getMessage());
                callback.onFailure(throwable.getMessage());
            }
        });



        return disposable;
    }

}
