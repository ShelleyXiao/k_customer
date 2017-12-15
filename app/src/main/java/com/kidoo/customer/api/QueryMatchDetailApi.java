package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 14:52
 * Company: zx
 * Description:
 * FIXME
 */


public class QueryMatchDetailApi {

    public static Observable<KidooApiResult<CompetionDetailResult>> queryMatchDetail(String matchId) {

        CustomerRequset requset = KidooApiManager.custom().build()
                .params("matchId", matchId)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<CompetionDetailResult>> observable =
                requset.call(kidooApiService.queryMatchDetail(requset.getParams().urlParamsMap));

        return observable;
    }

}
