package com.kidoo.customer.api;

import com.kidoo.customer.bean.NewBananersResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;


/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 16:45
 * Company: zx
 * Description:
 * FIXME
 */


public class BannerApi {

    public static Observable<KidooApiResult<NewBananersResult>> queryBannerList() {
        CustomerRequset requset = KidooApiManager.custom().build();

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<NewBananersResult>> observable = requset.call(kidooApiService.queryBannerList());

        return observable;
    }

}
