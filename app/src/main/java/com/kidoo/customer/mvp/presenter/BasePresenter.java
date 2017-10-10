package com.kidoo.customer.mvp.presenter;


import com.kidoo.customer.mvp.view.BaseView;

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

    void subscribe();

    void unsubscribe();
}
