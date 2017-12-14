package com.kidoo.customer.kidoohttp.http.function;


import com.kidoo.customer.kidoohttp.http.exception.ApiException;
import com.kidoo.customer.utils.LogUtils;

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


public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        LogUtils.e(throwable.getMessage());
        return Observable.error(ApiException.handleException(throwable));
    }
}
