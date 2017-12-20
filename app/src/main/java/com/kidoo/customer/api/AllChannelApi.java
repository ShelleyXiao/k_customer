package com.kidoo.customer.api;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.function.RetryExceptionFunc;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;
import com.kidoo.customer.kidoohttp.http.utils.RxUtil;
import com.kidoo.customer.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * User: ShaudXiao
 * Date: 2017-11-28
 * Time: 10:56
 * Company: zx
 * Description:
 * FIXME
 */


public class AllChannelApi {

    public static Observable<KidooApiResult<AllChannelResultBean>> queryAllChannels() {
        CustomerRequset requset = KidooApiManager.custom().build();

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<AllChannelResultBean>> observable = requset.call(kidooApiService.queryAllChannels());

        return observable;
    }

    public static Observable<ResponseBody> queryAllChannelsJson() {
        CustomerRequset requset = KidooApiManager.custom().build();
        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        LogUtils.i("test");
        return kidooApiService.queryAllChannelsJson();
    }

}
