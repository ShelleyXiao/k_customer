package com.kidoo.customer.mvp.interactor.account;

import com.kidoo.customer.api.GetInitDataApi;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.InitDataContract;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

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
}
