package com.kidoo.customer.api;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.UsersBaseResult;
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


public class QueryUserBaseInfoApi {

    public static Observable<KidooApiResult<UsersBaseResult>> queryUserBaseInfoAction(String ids, String imids, String mobiles) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("customerIds", ids)
                .params("imIds", imids)
                .params("mobiles", mobiles)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());
        ;

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<UsersBaseResult>> observable = requset.call(kidooApiService.queryUserBaseInfo(requset.getParams().urlParamsMap));

        return observable;

    }
}
