package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.mvp.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-11-21
 * Time: 15:55
 * Company: zx
 * Description:
 * FIXME
 */


public class BasePresenterImpl <T extends BaseView> implements BasePresenter<T> {

    protected T mPresenterView;

    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T view) {
        this.mPresenterView = view;
    }

    @Override
    public void dropView() {
        this.mPresenterView = null;
        unsubscribe();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }

        if(null != disposable) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void unsubscribe() {
        if(null != mCompositeDisposable) {
            mCompositeDisposable.clear();
        }
    }
}
