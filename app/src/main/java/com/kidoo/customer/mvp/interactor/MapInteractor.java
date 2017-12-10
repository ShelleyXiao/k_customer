package com.kidoo.customer.mvp.interactor;

import com.kidoo.customer.api.AllChannelApi;
import com.kidoo.customer.api.token.QueryArenaMapApi;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.ArenaListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.MapContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-08
 * Time: 14:51
 * Company: zx
 * Description:
 * FIXME
 */


public class MapInteractor implements MapContract.Interactor {

    @Inject
    public MapInteractor() {

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
    public Disposable QueryAreansAction(int channelCID, final GetAreansCallback callback) {
        Observable<KidooApiResult<ArenaListResult>> observable = QueryArenaMapApi.queryAllChannels(String.valueOf(channelCID));
        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<ArenaListResult>>() {
            @Override
            public void accept(KidooApiResult<ArenaListResult> arenaListResultKidooApiResult) throws Exception {
                if(arenaListResultKidooApiResult.isSuccess()) {
                    callback.onSuccess(arenaListResultKidooApiResult.getData().getArenaList());
                } else {
                    callback.onFailure(arenaListResultKidooApiResult.getErrorMsg());
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
