package com.kidoo.customer.api;

import com.kidoo.customer.bean.RegisterResultBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-11-27
 * Time: 18:29
 * Company: zx
 * Description:
 * FIXME
 */


public class RegisterApi {

    public static Observable<KidooApiResult<RegisterResultBean>> register(String phone, String pwd, String captcha) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("mobile", phone)
                .params("pwd", pwd)
                .params("captcha", captcha);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<RegisterResultBean>> observable = requset.call(kidooApiService.register(requset.getParams().urlParamsMap));

        return observable;
    }

}
