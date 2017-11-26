package com.kidoo.customer.api;

import com.kidoo.customer.bean.SMSCodeBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

/** 
 * description: 获取手机验证码
 * autour: ShaudXiao
 * date: 2017/11/26  
 * update: 2017/11/26
 * version: 
*/

public class GetSMSApi {

    public static Observable<KidooApiResult<SMSCodeBean>> getSMS(String phone, String type, String encrptyPhone) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("mobile", phone)
                .params("type", type)
                .params("token", encrptyPhone);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<SMSCodeBean>> observable = requset.call(kidooApiService.getSMS(requset.getParams().urlParamsMap));

        return observable;
    }

}
