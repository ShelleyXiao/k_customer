package com.kidoo.customer.mvp.interactor.team;

import com.kidoo.customer.api.JoinTeamApi;
import com.kidoo.customer.api.QueryTeamDetailApi;
import com.kidoo.customer.api.QueryUserBaseInfoApi;
import com.kidoo.customer.api.QuitTeamApi;
import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.bean.TeamDetailResult;
import com.kidoo.customer.bean.UsersBaseResult;
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
//        Disposable disposable1 = observable.flatMap(new Function<KidooApiResult<TeamDetailResult>, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<KidooApiResult<UsersBaseResult>> apply(KidooApiResult<TeamDetailResult> teamDetailResultKidooApiResult) throws Exception {
//
//                if(teamDetailResultKidooApiResult.isSuccess()) {
//                    callback.onSuccess(teamDetailResultKidooApiResult.getData());
//                } else {
//
//                }
//
//                StringBuilder idsStrs = new StringBuilder();
//                if(teamDetailResultKidooApiResult.getData().getTeam().getMemberList() != null) {
//                    for(TeamMemberBen bean : teamDetailResultKidooApiResult.getData().getTeam().getMemberList()) {
//                        idsStrs.append("@" + bean.getCustomerId());
//                    }
//                }
//
//                if(teamDetailResultKidooApiResult.getData().getApplyMemberList() != null) {
//                    for(TeamMemberBen bean : teamDetailResultKidooApiResult.getData().getApplyMemberList()) {
//                        idsStrs.append("@" + bean.getCustomerId());
//                    }
//                }
//
//
//
//                return QueryUserBaseInfoApi.queryUserBaseInfoAction(idsStrs.toString(), null, null);
//            }
//        }).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                KidooApiResult<UsersBaseResult> result = ( KidooApiResult<UsersBaseResult>)o;
//
//            }
//        });


        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<TeamDetailResult>>() {
            @Override
            public void accept(KidooApiResult<TeamDetailResult> teamBeanKidooApiResult) throws Exception {
                if (teamBeanKidooApiResult.isSuccess()) {
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

    @Override
    public Disposable queryMemebersInfoAction(String ids, final GetMemberInfoCallback callback) {
        Observable<KidooApiResult<UsersBaseResult>> observable = QueryUserBaseInfoApi.queryUserBaseInfoAction(ids, "", "");
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<UsersBaseResult>>() {
            @Override
            public void accept(KidooApiResult<UsersBaseResult> result) throws Exception {
                if (result.isSuccess()) {
                    callback.onSuccess(result.getData().getCustomerList());
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
    public Disposable joinTeamAction(String teamID, final JoinOrQuitCallback callback) {
        Observable<KidooApiResult<ReturnNullBean>> observable = JoinTeamApi.joinTeamAction(teamID);
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
    public Disposable quitTeamAction(String teamID, final JoinOrQuitCallback callback) {
        Observable<KidooApiResult<ReturnNullBean>> observable = QuitTeamApi.quitTeamAction(teamID);
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
