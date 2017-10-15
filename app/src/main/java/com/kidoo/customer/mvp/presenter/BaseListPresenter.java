package com.kidoo.customer.mvp.presenter;

/** 
 * description: 列表类型MVP Presenter 基础类
 * autour: ShaudXiao
 * date: 2017/10/15  
 * update: 2017/10/15
 * version: 
*/

public interface BaseListPresenter extends BasePresenter {
    void onRefreshing();

    void onLoadMore();
}
