package com.kidoo.customer.ui.base.activities;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.di.Component.ActivityComponent;
import com.kidoo.customer.di.Component.DaggerActivityComponent;
import com.kidoo.customer.di.module.ActivityModule;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;
import com.kidoo.customer.widget.SimplexToast;

/**
 * description:
 * autour: ShaudXiao
 * date: 2017/11/18
 * update: 2017/11/18
 * version:
 */

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView{

    protected ActivityComponent mActivityComponent;

    protected T mPresenter;


    @Override
    protected void initWidget() {
        super.initWidget();

        initActivityComponent();

        mPresenter = initInjector();
        mPresenter.attachView(this);


    }

    public void initActivityComponent() {

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((AppContext) getApplication()).getAppComponent())
                .build();

    }


    protected abstract T initInjector();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.dropView();
        }
    }

    @Override
    public void showToast(String msg) {
        SimplexToast.show(msg);
    }
}
