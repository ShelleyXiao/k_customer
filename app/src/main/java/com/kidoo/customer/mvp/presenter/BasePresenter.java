package com.kidoo.customer.mvp.presenter;


import com.kidoo.customer.mvp.view.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 9:15
 * Company: zx
 * Description:
 * FIXME
 */


public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void dropView();

    void addDisposable(Disposable disposable);

    void unsubscribe();
}
