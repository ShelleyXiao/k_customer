package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.MatchListResult;
import com.kidoo.customer.bean.QueryTeamResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/16.
 */

public class QueryTeamListApi {

    public static Observable<KidooApiResult<QueryTeamResult>> queryTeamList(String name, String channelCId, String pageNo, String pageSize) {

        CustomerRequset requset = KidooApiManager.custom().build()
                .params("name", name)
                .params("channelCId", channelCId)
                .params("pageNo", pageNo)
                .params("pageSize", pageSize)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<QueryTeamResult>> observable = requset.call(kidooApiService.queryTeamList(requset.getParams().urlParamsMap));

        return observable;

    }

}
