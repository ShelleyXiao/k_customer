package com.kidoo.customer.kidoohttp.function;


import com.kidoo.customer.kidoohttp.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * User: ShaudXiao
 * Date: 2017-11-21
 * Time: 11:21
 * Company: zx
 * Description: 异常转换处理
 * FIXME
 */


public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>>{

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ApiException.handleException(throwable));
    }
}
