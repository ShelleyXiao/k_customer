package com.kidoo.customer.kidoohttp.request;


import com.kidoo.customer.kidoohttp.function.RetryExceptionFunc;
import com.kidoo.customer.kidoohttp.transformer.HandleErrTransformer;
import com.kidoo.customer.kidoohttp.utils.RxUtil;
import com.kidoo.customer.kidoohttp.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.ResponseBody;

/**
 * User: ShaudXiao
 * Date: 2017-11-20
 * Time: 18:33
 * Company: zx
 * Description:
 * FIXME
 */


public class CustomerRequset extends BaseRequest<CustomerRequset> {

    public CustomerRequset() {
        super("");
    }

    @Override
    public CustomerRequset build() {
        return super.build();
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return null;
    }

    /**
     * 创建api服务
     *
     * @param service 自定义的apiservice class
     */
    public <T> T create(final Class<T> service) {
        checkvalidate();
        return retrofit.create(service);
    }

    private void checkvalidate() {
        Utils.checkNotNull(retrofit, "请先在调用build()才能使用");
    }

    /**
     * 调用call返回一个Observable<T>
     * 举例：如果你给的是一个Observable<KidooApiResult<AuthModel>> 那么返回的<T>是一个ApiResult<AuthModel>
     */
    public <T> Observable<T> call(Observable<T> observable) {
        checkvalidate();
        return observable.compose(RxUtil.io_main())
                .compose(new HandleErrTransformer())
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, tretryIncreaseDelay));
    }

//    public <T> void call(Observable<T> observable, CallBack<T> callBack) {
//        call(observable, new CallBackSubsciber(context, callBack));
//    }

    public <R> void call(Observable observable, Observer<R> subscriber) {
        observable.compose(RxUtil.io_main())
                .subscribe(subscriber);
    }


}
