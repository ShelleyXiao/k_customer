package com.kidoo.customer.api;

import com.kidoo.customer.bean.CheckAllTokenBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-11-28
 * Time: 10:56
 * Company: zx
 * Description:
 * FIXME
 */


public class CheckAllTokenApi {

    public static Observable<KidooApiResult<CheckAllTokenBean>> checkAllToken(String customId, String token, String tokenId) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("customId", customId)
                .params("tokenId", tokenId)
                .params("token", token);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<CheckAllTokenBean>> observable = requset.call(kidooApiService.checkAllToken(requset.getParams().urlParamsMap));

        return observable;
    }

}
