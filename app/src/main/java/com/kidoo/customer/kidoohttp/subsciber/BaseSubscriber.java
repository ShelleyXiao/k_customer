package com.kidoo.customer.kidoohttp.subsciber;

import android.content.Context;

import com.kidoo.customer.kidoohttp.exception.ApiException;
import com.kidoo.customer.kidoohttp.utils.HttpLogger;
import com.kidoo.customer.kidoohttp.utils.Utils;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;

/**
 * User: ShaudXiao
 * Date: 2017-11-21
 * Time: 13:58
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    public WeakReference<Context> mContextWeakReference;

    public BaseSubscriber() {

    }

    public BaseSubscriber(Context context) {
        mContextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    protected void onStart() {
        if (mContextWeakReference != null && mContextWeakReference.get() != null
                && !Utils.isNetworkAvailable(mContextWeakReference.get())) {
            onComplete();
        }
    }

    @Override
    public void onNext(T t) {
        HttpLogger.e("---http is onNext---");
    }

    @Override
    public void onError(Throwable e) {
        HttpLogger.e("---http is onError---");
        if (e instanceof ApiException) {
            HttpLogger.e("--> e instanceof ApiException err:" + e);
            onError((ApiException) e);
        } else {
            HttpLogger.e("--> e !instanceof ApiException err:" + e);
            onError(ApiException.handleException(e));
        }
    }

    @Override
    public void onComplete() {
        HttpLogger.e("---http is onComplete---");
    }

    public abstract void onError(ApiException e);
}
