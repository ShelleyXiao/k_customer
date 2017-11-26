package com.kidoo.customer.api;

import com.kidoo.customer.bean.LoginResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/11/25  
 * update: 2017/11/25
 * version: 
*/

public class LoginApi {

    public static Observable<KidooApiResult<LoginResult>> login(String userName, String pwd, String loginType) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("mobile", userName)
                .params("pwd", pwd)
                .params("loginType", loginType);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<LoginResult>> observable = requset.call(kidooApiService.login(requset.getParams().urlParamsMap));

        return observable;

    }

}
