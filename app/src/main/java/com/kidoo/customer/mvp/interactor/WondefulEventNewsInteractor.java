package com.kidoo.customer.mvp.interactor;

import com.kidoo.customer.api.HomeNewsApi;
import com.kidoo.customer.bean.NewsListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.WondefulEventNewsContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 16:53
 * Company: zx
 * Description:
 * FIXME
 */


public class WondefulEventNewsInteractor implements WondefulEventNewsContract.Interactor {


    @Inject
    public WondefulEventNewsInteractor() {

    }


    @Override
    public Disposable queryNewsAction(int type, int pageSize, int pageNo, final WondefulEventNewsContract.Interactor.Callback callback) {
        Observable<KidooApiResult<NewsListResult>> observable1 = HomeNewsApi.queryHomeNewsList(String.valueOf(type)
                , String.valueOf(pageSize), String.valueOf(pageNo));

        Disposable disposable = observable1.subscribe(new Consumer<KidooApiResult<NewsListResult>>() {
            @Override
            public void accept(KidooApiResult<NewsListResult> newsListResultKidooApiResult) throws Exception {
                if (newsListResultKidooApiResult.isSuccess()) {
                    callback.onSuccess(newsListResultKidooApiResult);
                } else {
                    callback.onFailure(newsListResultKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFailure(throwable.getMessage());
            }
        });

        return disposable;
    }

    @Override
    public Disposable queryList(int type, final WondefulEventNewsContract.Interactor.Callback callback) {
        Observable<KidooApiResult<NewsListResult>> observable = HomeNewsApi.queryHomeNewsList(String.valueOf(type));

        Disposable disposable = observable.subscribe(new Consumer<KidooApiResult<NewsListResult>>() {
            @Override
            public void accept(KidooApiResult<NewsListResult> newsListResultKidooApiResult) throws Exception {
                if (newsListResultKidooApiResult.isSuccess()) {
                    callback.onSuccess(newsListResultKidooApiResult);
                } else {
                    callback.onFailure(newsListResultKidooApiResult.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFailure(throwable.getMessage());
            }
        });


        return disposable;
    }


}
