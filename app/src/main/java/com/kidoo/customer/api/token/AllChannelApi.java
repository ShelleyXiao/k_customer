package com.kidoo.customer.api.token;

import com.kidoo.customer.api.KidooApiService;
import com.kidoo.customer.bean.AllChannelResultBean;
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


public class AllChannelApi {

    public static Observable<KidooApiResult<AllChannelResultBean>> queryAllChannels() {
        CustomerRequset requset = KidooApiManager.custom().build();

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<AllChannelResultBean>> observable = requset.call(kidooApiService.queryAllChannels());

        return observable;
    }

}
