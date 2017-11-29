package com.kidoo.customer.kidoohttp.http.func;

import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.exception.ServerException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * <p>描述：ApiResult<T>转换T</p>
 * 作者： zhouyou<br>
 * 日期： 2017/5/15 16:54 <br>
 * 版本： v1.0<br>
 */
public class HandleFuc<T> implements Function<KidooApiResult<T>, KidooApiResult<T>> {
    @Override
    public KidooApiResult<T> apply(@NonNull KidooApiResult<T> tApiResult) throws Exception {
        if (tApiResult.isSuccess()) {
            return tApiResult;// == null ? Optional.ofNullable(tApiResult.getData()).orElse(null) : tApiResult.getData();
        } else {
            throw new ServerException(tApiResult.getErrorCode(), tApiResult.getErrorMsg());
        }
    }
}
