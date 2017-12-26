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
 * Date: 2017-12-20
 * Time: 11:11
 * Company: zx
 * Description:
 * FIXME
 */


public class AddMatchNodeApi {

    public static Observable<KidooApiResult<ReturnNullBean>> addMatchNodeApi(String matchId,  String msg, String time, String showType, String pic, String picMini) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("matchId", matchId)
                .params("msg", msg)
                .params("timePoint", time)
                .params("showType", showType)
                .params("pic", pic)
                .params("picMini", picMini)
                .params("customerId", String.valueOf(AccountHelper.getUserId()))
                .params("token", TokenManager.getInstance().getToken());

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<ReturnNullBean>> observable = requset.call(kidooApiService.addMatchNode(requset.getParams().urlParamsMap));

        return observable;

    }
}
