package com.kidoo.customer.api;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-08
 * Time: 10:00
 * Company: zx
 * Description:
 * FIXME
 */


public class QueryPicInfoApi {

    public static Observable<KidooApiResult<QNToken>> queryPIcInfo() {
        CustomerRequset requset = KidooApiManager.custom().build();

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<QNToken>> observable = requset.call(kidooApiService.queryPicInfo());

        return observable;
    }
}
