package com.kidoo.customer.api;

import com.kidoo.customer.api.model.KeypairResult;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * User: ShaudXiao
 * Date: 2017-10-09
 * Time: 10:12
 * Company: zx
 * Description: api 接口
 * FIXME
 */


public interface ApiService {

    public static final String BASE_API_URL = "http://www.kidoo.site";

    @GET("customer/common/getKeyPair?mobile=2")
    Observable<KeypairResult> getKeyPair();


}
