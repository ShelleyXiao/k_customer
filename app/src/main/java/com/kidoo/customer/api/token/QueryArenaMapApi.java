package com.kidoo.customer.api.token;

import com.kidoo.customer.api.KidooApiService;
import com.kidoo.customer.bean.ArenaListResult;
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


public class QueryArenaMapApi {

    public static Observable<KidooApiResult<ArenaListResult>> queryAllChannels(String channelCID) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("channelCId", channelCID);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<ArenaListResult>> observable = requset.call(kidooApiService.queryArenaMap(requset.getParams().urlParamsMap));

        return observable;
    }

}
