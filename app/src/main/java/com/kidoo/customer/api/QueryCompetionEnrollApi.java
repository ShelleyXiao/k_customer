package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.EnrollSituationResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 10:28
 * Company: zx
 * Description:
 * FIXME
 */


public class QueryCompetionEnrollApi {

    public static Observable<KidooApiResult<EnrollSituationResult>> queryCompetionEnroll(String matchId) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("matchId", matchId)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<EnrollSituationResult>> observable = requset.call(kidooApiService.queryMatchEnroll(requset.getParams().urlParamsMap));

        return observable;
    }

}
