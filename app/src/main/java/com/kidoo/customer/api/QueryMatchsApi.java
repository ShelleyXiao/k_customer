package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.MatchListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;
import com.kidoo.customer.utils.LogUtils;

import io.reactivex.Observable;


/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 14:36
 * Company: zx
 * Description:
 * FIXME
 */


public class QueryMatchsApi {

    public static Observable<KidooApiResult<MatchListResult>> queryMatchS(String channelCID, String pageNo, String pageSize) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("channelCId", channelCID)
                .params("pageNo", pageNo)
                .params("pageSize", pageSize)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<MatchListResult>> observable = requset.call(kidooApiService.queryMatchList(requset.getParams().urlParamsMap));

        return observable;
    }

    public static Observable<KidooApiResult<MatchListResult>> queryMatchS(String channelCID) {
        LogUtils.e("");
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("channelCId", channelCID)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<MatchListResult>> observable = requset.call(kidooApiService.queryMatchList(requset.getParams().urlParamsMap));

        return observable;
    }

}
