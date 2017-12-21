package com.kidoo.customer.mvp.interactor.channelCampaign;

import com.kidoo.customer.api.AllChannelApi;
import com.kidoo.customer.api.QueryMatchsApi;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.MatchListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.function.RetryExceptionFunc;
import com.kidoo.customer.kidoohttp.http.utils.JsonParseUtils;
import com.kidoo.customer.kidoohttp.http.utils.RxUtil;
import com.kidoo.customer.mvp.contract.channelCampaign.ChannelCampaignContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

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
//        Observable<KidooApiResult<AllChannelResultBean>> observable = AllChannelApi.queryAllChannels();
//        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<AllChannelResultBean>>() {
//            @Override
//            public void accept(KidooApiResult<AllChannelResultBean> allChannelResultBeanKidooApiResult) throws Exception {
//                if (allChannelResultBeanKidooApiResult.isSuccess()) {
//                    callback.onSuccess(allChannelResultBeanKidooApiResult.getData());
//                } else {
//                    callback.onFailure(allChannelResultBeanKidooApiResult.getErrorMsg());
//                }
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                LogUtils.e("" + throwable.getMessage()
//                        + "\n"
//                        + throwable.getLocalizedMessage());
//                callback.onFailure(throwable.getMessage());
//            }
//        });


        Observable observable_ = AllChannelApi.queryAllChannelsJson();
        Observable observable1 = observable_.compose(RxUtil.io_main())
                .retryWhen(new RetryExceptionFunc(KidooApiManager.getRetryCount(),
                        KidooApiManager.getRetryDelay(),
                        KidooApiManager.getRetryIncreaseDelay()))
                .map(new Function() {
                    @Override
                    public Object apply(Object o) throws Exception {
                        ResponseBody body = (ResponseBody) o;
                        String json = body.string();

                        KidooApiResult<AllChannelResultBean> result = JsonParseUtils.parseAllChannel(json);

                        return result;
                    }
                });
        Disposable disposable1 = observable1.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                KidooApiResult<AllChannelResultBean> result = (KidooApiResult<AllChannelResultBean>) o;
                if (result.isSuccess()) {
                    callback.onSuccess(result.getData());
//                    LogUtils.i(result.getData().getChannelCmaps().get(0).toString());
                } else {
                    callback.onFailure(result.getErrorMsg());
                    LogUtils.e(result.getErrorMsgDev());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFailure(throwable.getMessage());
                LogUtils.e(throwable.getMessage());
            }
        });


        return disposable1;
    }

    @Override
    public Disposable queryMoreMatchsAction(int channelCID, int pageSize, int pageNo, final GetMatchsCallback callback) {
        Observable<KidooApiResult<MatchListResult>> observable = QueryMatchsApi.queryMatchS(String.valueOf(channelCID)
                , String.valueOf(pageNo), String.valueOf(pageSize));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<MatchListResult>>() {
            @Override
            public void accept(KidooApiResult<MatchListResult> matchListResultKidooApiResult) throws Exception {
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

    @Override
    public Disposable queryMacths(int channelCID, final GetMatchsCallback callback) {
        Observable<KidooApiResult<MatchListResult>> observable = QueryMatchsApi.queryMatchS(String.valueOf(channelCID));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<MatchListResult>>() {
            @Override
            public void accept(KidooApiResult<MatchListResult> matchListResultKidooApiResult) throws Exception {

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
