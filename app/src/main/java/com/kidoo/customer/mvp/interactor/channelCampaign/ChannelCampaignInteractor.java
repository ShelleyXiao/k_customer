package com.kidoo.customer.mvp.interactor.channelCampaign;

import com.kidoo.customer.api.AllChannelApi;
import com.kidoo.customer.api.QueryMatchsApi;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.MatchListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.channelCampaign.ChannelCampaignContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 14:32
 * Company: zx
 * Description:
 * FIXME
 */


public class ChannelCampaignInteractor implements ChannelCampaignContract.Interactor {


    @Inject
    public ChannelCampaignInteractor() {

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

    @Override
    public Disposable queryMoreMatchsAction(int channelCID, int pageSize, int pageNo, final GetMatchsCallback callback) {
        Observable<KidooApiResult<MatchListResult>> observable = QueryMatchsApi.queryMatchS(String.valueOf(channelCID)
                , String.valueOf(pageNo), String.valueOf(pageSize));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<MatchListResult>>() {
            @Override
            public void accept(KidooApiResult<MatchListResult> matchListResultKidooApiResult) throws Exception {
                if(matchListResultKidooApiResult.isSuccess()) {
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

    @Override
    public Disposable queryMacths(int channelCID, final GetMatchsCallback callback) {
        Observable<KidooApiResult<MatchListResult>> observable = QueryMatchsApi.queryMatchS(String.valueOf(channelCID));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<MatchListResult>>() {
            @Override
            public void accept(KidooApiResult<MatchListResult> matchListResultKidooApiResult) throws Exception {

                if(matchListResultKidooApiResult.isSuccess()) {
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