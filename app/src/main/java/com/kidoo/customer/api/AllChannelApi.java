package com.kidoo.customer.api;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;

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

//    public static Observable<AllChannelResultBean> queryAllChannelsJson() {
//        CustomerRequset requset = KidooApiManager.custom().build();
//        KidooApiService kidooApiService = requset.create(KidooApiService.class);
//        KidooApiManager  config = KidooApiManager.getInstance();
//        return kidooApiService.queryAllChannelsJson()
//                .compose(RxUtil.io_main())
//                .retryWhen(new RetryExceptionFunc(KidooApiManager.getRetryCount(),
//                        KidooApiManager.getRetryDelay(),
//                        KidooApiManager.getRetryIncreaseDelay()))
//                .map(new Function<Object, ResponseBody>() {
//                    @Override
//                    public ResponseBody apply(Object o) throws Exception {
//                        return null;
//                    }
//                });
//                ;
//    }

}
