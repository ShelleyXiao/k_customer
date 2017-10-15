package com.kidoo.customer.mvp.view;

import com.kidoo.customer.mvp.presenter.BasePresenter;

/**
 * User: ShaudXiao
 * Date: 2017-09-30
 * Time: 15:44
 * Company: zx
 * Description:
 * FIXME
 */


public interface BaseView<Presenter extends BasePresenter> {


    void setPresenter(Presenter presenter);

    void showNetworkError(String str);

}
