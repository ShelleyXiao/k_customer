package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.mvp.view.BaseView;

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

    @Override
    public void attachView(T view) {
        this.mPresenterView = view;
    }

    @Override
    public void dropView() {
        this.mPresenterView = null;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
