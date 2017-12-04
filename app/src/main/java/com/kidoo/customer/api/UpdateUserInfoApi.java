package com.kidoo.customer.api;

import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-04
 * Time: 17:58
 * Company: zx
 * Description:
 * FIXME
 */


public class UpdateUserInfoApi {

    public static Observable<KidooApiResult<ReturnNullBean>> updateUserInfoApi(String customerId, String realName,
                                                                               String nickName, String email,
                                         String portait, String sex, String brithday, String sign) {

        CustomerRequset requset = KidooApiManager.custom().build()
                .params("mobile", customerId)
                .params("sex", sex)
                .params("portrait", portait)
                .params("email", email)
                .params("realName", realName)
                .params("birthday", brithday)
                .params("nickName", nickName)
                .params("sign", sign);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<ReturnNullBean>> observable = requset.call(kidooApiService.updateUserInfo(requset.getParams().urlParamsMap));

        return observable;

    }
}
