package com.kidoo.customer.api;

import com.kidoo.customer.bean.CheckTokenIdBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-13
 * Time: 16:01
 * Company: zx
 * Description:
 * FIXME
 */


public class CheckTokenIdApi {

    public static Observable<KidooApiResult<CheckTokenIdBean>> checkTokenId(String customId, String tokenId) {
        CustomerRequset request = KidooApiManager.custom().build()
                .removeParam("token") //移除参数 与普通请求却别开
                .params("customerId", customId)
                .params("tokenId", tokenId);

        KidooApiService kidooApiService = request.create(KidooApiService.class);
        Observable<KidooApiResult<CheckTokenIdBean>> observable = request.call(kidooApiService.checkTokenId(request.getParams().urlParamsMap));

        return observable;

    }

}
