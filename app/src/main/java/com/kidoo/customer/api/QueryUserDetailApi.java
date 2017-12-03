package com.kidoo.customer.api;

import com.kidoo.customer.bean.UserDetailBean;
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

public class QueryUserDetailApi {

    public static Observable<KidooApiResult<UserDetailBean>> queryDetailAction(String phone) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("mobile", phone);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<UserDetailBean>> observable = requset.call(kidooApiService.queryDetail(requset.getParams().urlParamsMap));

        return observable;

    }
}
