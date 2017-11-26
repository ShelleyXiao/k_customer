package com.kidoo.customer.api;

import com.kidoo.customer.bean.KeypairResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * description:
 * autour: ShaudXiao
 * date: 2017/11/25
 * update: 2017/11/25
 * version:
*/

public class GetTempRSAKeyPairApi {

    public static Observable<KidooApiResult<KeypairResult>> getTempRSAKeyPair(String phone) {
        CustomerRequset requset = KidooApiManager.custom().build().params("mobile", phone);
        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<KeypairResult>> observable = requset.call(kidooApiService.getRSAKeyPair(requset.getParams().urlParamsMap));

        return observable;

    }

}
