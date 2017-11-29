package com.kidoo.customer.component;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.kidoo.customer.utils.LogUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-11-29
 * Time: 16:40
 * Company: zx
 * Description: 事件主线
 * FIXME
 */


public class RxBus {

    private static RxBus sInstance;
    private Relay<Object> bus = null;

    private Map<Class<?>, Object> mStickyEventMap = null;

    private RxBus() {
        bus = PublishRelay.create().toSerialized();

        if (mStickyEventMap == null){
            mStickyEventMap = new ConcurrentHashMap<>();
        }
    }

    public static RxBus getDefault() {
        if(null == sInstance) {
            synchronized (RxBus.class) {
                if(null == sInstance) {
                    sInstance = new RxBus();
                }
            }
        }

        return sInstance;
    }


    public void post(Object event) {
        bus.accept(event);
    }

    public  <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext) {
        return toObservable(eventType).observeOn(scheduler).subscribe(onNext);
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError,
                                   Action onComplete, Consumer onSubscribe) {
        return toObservable(eventType).observeOn(scheduler).subscribe(onNext, onError, onComplete, onSubscribe);
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError,
                                   Action onComplete) {
        return toObservable(eventType).observeOn(scheduler).subscribe(onNext, onError, onComplete);
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError) {
        return toObservable(eventType).observeOn(scheduler).subscribe(onNext, onError);
    }

    public <T> Disposable register(Class<T> eventType, Consumer<T> onNext) {
        return toObservable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
    }

    public <T> Disposable register(Class<T> eventType, Consumer<T> onNext, Consumer onError,
                                   Action onComplete, Consumer onSubscribe) {
        return toObservable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete, onSubscribe);
    }

    public <T> Disposable register(Class<T> eventType, Consumer<T> onNext, Consumer onError,
                                   Action onComplete) {
        return toObservable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete);
    }

    public <T> Disposable register(Class<T> eventType, Consumer<T> onNext, Consumer onError) {
        return toObservable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError);
    }

    public void unregister(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    /**
     * Stciky 相关
     */

    /**
     * 发送一个Stciky事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            if(null != mStickyEventMap) {
                mStickyEventMap.put(event.getClass(), event);
            }
        }

        post(event);
    }

    public <T> Disposable registerSticky(Class<T> eventType, Consumer<T> onNext, Consumer onError) {
        return toObservableSticky(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType ) {
        synchronized (mStickyEventMap) {
            Object event = null;
            Observable<T> tObservable = null;
            Observable<T> observable = bus.ofType(eventType);

            if(null != mStickyEventMap) {
                event = mStickyEventMap.get(eventType);
            }
            if(null != event) {
                final Object finalEvent = event;
                try {
                    tObservable = observable.mergeWith(Observable.create(new ObservableOnSubscribe<T>() {
                        @Override
                        public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                            try {
                                emitter.onNext(eventType.cast(finalEvent));
                            } catch (OnErrorNotImplementedException e) {

                            }
                        }
                    }));
                } catch (Exception e) {
                    LogUtils.e("RxBus: " + e.getMessage());
                } finally {
                    return tObservable;
                }
            } else {
                return observable;
            }
        }
    }

}
