package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-19
 * Time: 15:53
 * Company: zx
 * Description:
 * FIXME
 */


public class QuitTeamApi {

    public static Observable<KidooApiResult<ReturnNullBean>> quitTeamAction(String teamId) {

        CustomerRequset requset = KidooApiManager.custom().build()
                .params("teamId", teamId)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<ReturnNullBean>> observable = requset.call(kidooApiService.quitTeam(requset.getParams().urlParamsMap));

        return observable;

    }

}
