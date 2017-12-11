package com.kidoo.customer.api;

import com.kidoo.customer.bean.NewsListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.request.CustomerRequset;

import io.reactivex.Observable;


/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 16:45
 * Company: zx
 * Description:
 * FIXME
 */


public class HomeNewsApi {

    public static Observable<KidooApiResult<NewsListResult>> queryHomeNewsList(String type) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("type", type);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<NewsListResult>> observable = requset.call(kidooApiService.queryNewsList(requset.getParams().urlParamsMap));

        return observable;
    }

    public static Observable<KidooApiResult<NewsListResult>> queryHomeNewsList(String type, String pageSize, String pageNO) {
        CustomerRequset requset = KidooApiManager.custom().build()
                .params("type", type)
                .params("pageSize", pageSize)
                .params("pageNo", pageNO);

        KidooApiService kidooApiService = requset.create(KidooApiService.class);
        Observable<KidooApiResult<NewsListResult>> observable = requset.call(kidooApiService.queryNewsList(requset.getParams().urlParamsMap));

        return observable;
    }
}
