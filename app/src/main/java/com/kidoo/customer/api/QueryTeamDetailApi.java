package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * description: 查询用户详细资料api
 * autour: ShaudXiao
 * date: 2017/12/2
 * update: 2017/12/2
 * version:
 */

public class QueryTeamDetailApi {

    public static Observable<KidooApiResult<TeamBean>> queryDetailAction(String teamId) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("teamId", teamId)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<TeamBean>> observable = requset.call(kidooApiService.queryTeamDetail(requset.getParams().urlParamsMap));

        return observable;

    }
}
