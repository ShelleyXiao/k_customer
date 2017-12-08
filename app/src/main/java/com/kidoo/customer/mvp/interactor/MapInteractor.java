package com.kidoo.customer.mvp.interactor;

import com.kidoo.customer.api.AllChannelApi;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.MapContract;

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
                if(allChannelResultBeanKidooApiResult.isSuccess()) {
                    callback.onSuccess(allChannelResultBeanKidooApiResult.getData());
                } else {
                    callback.onFailure(allChannelResultBeanKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFailure(throwable.getMessage());
            }
        });

        return null;
    }
}
