package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.TeamsBaseInfoResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-19
 * Time: 15:04
 * Company: zx
 * Description:
 * FIXME
 */


public class QueryTeamsBaseInfoApi {

    public static Observable<KidooApiResult<TeamsBaseInfoResult>> queryUserBaseInfo(String ids) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("teamIds", ids)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());
        ;

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<TeamsBaseInfoResult>> observable
                = requset.call(kidooApiService.queryTeamBaseInfo(requset.getParams().urlParamsMap));

        return observable;

    }
}
