package com.kidoo.customer.kidoohttp.http.transformer;


import com.kidoo.customer.kidoohttp.http.function.HttpResponseFunc;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * User: ShaudXiao
 * Date: 2017-11-21
 * Time: 11:20
 * Company: zx
 * Description: 错误转换Transformer
 * FIXME
 */


public class HandleErrTransformer<T> implements ObservableTransformer<T, T> {

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {

        return upstream.onErrorResumeNext(new HttpResponseFunc<T>());
    }
}
