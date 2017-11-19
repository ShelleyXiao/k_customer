package com.kidoo.customer.mvp.view;

import com.kidoo.customer.mvp.presenter.BaseListPresenter;

import java.util.List;

/**
 * Created by haibin
 * on 2016/11/30.
 */

public interface BaseListView<Presenter extends BaseListPresenter, M> extends BaseView {
    /**
     * 刷新成功
     */
    void onRefreshSuccess(List<M> data);

    /**
     * 加载成功
     */
    void onLoadMoreSuccess(List<M> data);

    /**
     * 没有更多数据
     */
    void showMoreMore();

    /**
     * 加载完成
     */
    void onComplete();
}
