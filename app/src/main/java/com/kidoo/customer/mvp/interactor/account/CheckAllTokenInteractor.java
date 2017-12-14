package com.kidoo.customer.mvp.interactor.account;

import com.kidoo.customer.api.CheckAllTokenApi;
import com.kidoo.customer.bean.CheckAllTokenBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.CheckAllTokenContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-11-28
 * Time: 17:32
 * Company: zx
 * Description:
 * FIXME
 */


public class CheckAllTokenInteractor implements CheckAllTokenContract.Interactor {

    @Inject
    public  CheckAllTokenInteractor() {}

    @Override
    public void doCheckAllToken(String customId, String token, String tokenId, final CheckAllTokenCallback callback) {
        Observable<KidooApiResult<CheckAllTokenBean>> observable = CheckAllTokenApi.checkAllToken(customId, token, tokenId);
        observable.subscribe(new Consumer<KidooApiResult<CheckAllTokenBean>>() {
            @Override
            public void accept(KidooApiResult<CheckAllTokenBean> checkAllTokenBeanKidooApiResult) throws Exception {
                if(checkAllTokenBeanKidooApiResult.isSuccess()) {
                    callback.onSuccess(checkAllTokenBeanKidooApiResult.getData());
                } else {
                    callback.onFailure(checkAllTokenBeanKidooApiResult.getErrorMsg());
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
