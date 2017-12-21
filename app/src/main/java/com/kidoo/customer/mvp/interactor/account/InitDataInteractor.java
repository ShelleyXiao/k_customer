package com.kidoo.customer.mvp.interactor.account;

import com.kidoo.customer.api.AllChannelApi;
import com.kidoo.customer.api.GetInitDataApi;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.function.RetryExceptionFunc;
import com.kidoo.customer.kidoohttp.http.utils.JsonParseUtils;
import com.kidoo.customer.kidoohttp.http.utils.RxUtil;
import com.kidoo.customer.mvp.contract.InitDataContract;
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
 * Date: 2017-11-28
 * Time: 11:31
 * Company: zx
 * Description:
 * FIXME
 */


public class InitDataInteractor implements InitDataContract.Interactor {

    @Inject
    public InitDataInteractor() {}


    @Override
    public void doGetInitData(String version, String appType, final InitDataCallback callback) {
        Observable<KidooApiResult<InitData>> observable = GetInitDataApi.getInitData(version, appType);
        observable.subscribe(new Consumer<KidooApiResult<InitData>>() {
            @Override
            public void accept(KidooApiResult<InitData> initDataKidooApiResult) throws Exception {
                if(initDataKidooApiResult.isSuccess()) {
                    LogUtils.i(initDataKidooApiResult.getData().toString());
                    callback.onSuccess(initDataKidooApiResult.getData());
                } else {
                    callback.onFailure(initDataKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFailure(throwable.getMessage());
            }
        });
    }


    public Disposable queryAllChannelsAction(final ChannelCampaignContract.Interactor.GetAllChannelsCallback callback) {

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


}
