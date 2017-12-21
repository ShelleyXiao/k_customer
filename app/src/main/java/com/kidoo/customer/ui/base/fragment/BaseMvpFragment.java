package com.kidoo.customer.ui.base.fragment;

import android.os.Bundle;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.di.Component.DaggerFragmentComponent;
import com.kidoo.customer.di.Component.FragmentComponent;
import com.kidoo.customer.di.module.FragmentModule;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;
import com.kidoo.customer.widget.SimplexToast;

import io.reactivex.annotations.Nullable;

/**
 * description:
 * autour: ShaudXiao
 * date: 2017/11/19
 * update: 2017/11/19
 * version:
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    protected FragmentComponent mFragmentComponent;
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponent();

        mPresenter = initInjector();
        mPresenter.attachView(this);

    }

    private void initFragmentComponent() {

        mFragmentComponent = DaggerFragmentComponent.builder().fragmentModule(new FragmentModule(this))
                .appComponent(((AppContext) getActivity().getApplication()).getAppComponent())
                .build();
    }

    protected abstract T initInjector();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.dropView();
        }
    }

    @Override
    public void showToast(String msg) {
        SimplexToast.show(msg);
    }
}
