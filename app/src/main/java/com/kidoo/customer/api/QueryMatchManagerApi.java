package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.MyMatchsResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-21
 * Time: 14:55
 * Company: zx
 * Description:
 * FIXME
 */


public class QueryMatchManagerApi {

    public static Observable<KidooApiResult<MyMatchsResult>> queryMatchManager(String customerId2Query, String pageNo, String pageSize) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("customerId2Query", customerId2Query)
                .params("pageNo", pageNo)
                .params("pageSize", pageSize)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<MyMatchsResult>> observable = requset.call(kidooApiService.queryMyManageList(requset.getParams().urlParamsMap));

        return observable;
    }

    public static Observable<KidooApiResult<MyMatchsResult>> queryMatchManager(String customerId2Query) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("customerId2Query", customerId2Query)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<MyMatchsResult>> observable = requset.call(kidooApiService.queryMyManageList(requset.getParams().urlParamsMap));

        return observable;
    }
}
