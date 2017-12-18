package com.kidoo.customer.mvp.interactor.team;

import com.kidoo.customer.api.AllChannelApi;
import com.kidoo.customer.api.QueryTeamListApi;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.QueryTeamResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.team.QueryTeamContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ShaudXiao on 2017/12/17.
 */

public class QueryTeamInteractor implements QueryTeamContract.Interactor{

    @Inject
    public QueryTeamInteractor() {

    }

    @Override
    public Disposable queryTeamListAction(String name,int channelCID, int pageNO, int pageSize, final GetTeamListCallback callback) {
        Observable<KidooApiResult<QueryTeamResult>> observable = QueryTeamListApi.queryTeamList(name,
                String.valueOf(channelCID),
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



    @Override
    public Disposable queryAllChannelsAction(final GetAllChannelsCallback callback) {
        Observable<KidooApiResult<AllChannelResultBean>> observable = AllChannelApi.queryAllChannels();
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<AllChannelResultBean>>() {
            @Override
            public void accept(KidooApiResult<AllChannelResultBean> allChannelResultBeanKidooApiResult) throws Exception {
                if (allChannelResultBeanKidooApiResult.isSuccess()) {
                    LogUtils.i("requst scuess");
                    callback.onSuccess(allChannelResultBeanKidooApiResult.getData());
                } else {
                    LogUtils.e(allChannelResultBeanKidooApiResult.getErrorMsgDev());
                    callback.onFailure(allChannelResultBeanKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e("" + throwable.getMessage()
                        + "\n"
                        + throwable.getLocalizedMessage());
                callback.onFailure(throwable.getMessage());
            }
        });

        return disposable;
    }
}
