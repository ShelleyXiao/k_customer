package com.kidoo.customer.api;

import com.kidoo.customer.bean.InitData;
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


public class GetInitDataApi {

    public static Observable<KidooApiResult<InitData>> getInitData(String version, String appType) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("versionCode", version)
                .params("appType", appType);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<InitData>> observable = requset.call(kidooApiService.getInitData(requset.getParams().urlParamsMap));

      //  return observable.map(new HandleFuc<InitData>());
        return observable;
    }

}
