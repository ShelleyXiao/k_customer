package com.kidoo.customer.kidoohttp.function;


import com.kidoo.customer.kidoohttp.exception.ApiException;
import com.kidoo.customer.kidoohttp.utils.HttpLogger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * User: ShaudXiao
 * Date: 2017-11-21
 * Time: 11:38
 * Company: zx
 * Description:
 * FIXME
 */


public class RetryExceptionFunc implements Function<Observable<? extends Throwable>, Observable<?>> {

    private int count = 0;

    private long delay = 500;

    private long increaseDealy = 3000;

    public RetryExceptionFunc() {
    }

    public RetryExceptionFunc(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    public RetryExceptionFunc(int count, long delay, long increaseDelay) {
        this(count, delay);
        this.increaseDealy = increaseDelay;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) {

        return observable.zipWith(Observable.range(1, count + 1), new BiFunction<Throwable, Integer, Wrapper>() {
            @Override
            public Wrapper apply(Throwable throwable, Integer integer) throws Exception {
                return new Wrapper(throwable, integer);
            }
        }).flatMap(new Function<Wrapper, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Wrapper wrapper) throws Exception {
                if (wrapper.index > 1)
                    HttpLogger.i("重试次数：" + (wrapper.index));
                int errCode = 0;
                if (wrapper.throwable instanceof ApiException) {
                    ApiException exception = (ApiException) wrapper.throwable;
                }
                if ((wrapper.throwable instanceof ConnectException
                        || wrapper.throwable instanceof SocketTimeoutException
                        || errCode == ApiException.ERROR.NETWORD_ERROR
                        || errCode == ApiException.ERROR.TIMEOUT_ERROR
                        || wrapper.throwable instanceof SocketTimeoutException
                        || wrapper.throwable instanceof TimeoutException)
                        && wrapper.index < count + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                    return Observable.timer(delay + (wrapper.index - 1) * increaseDealy, TimeUnit.MILLISECONDS);

                }
                return Observable.error(wrapper.throwable);
            }
        });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
