package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

/**
 * description: 广播详情契约
 * autour: ShaudXiao
 * date: 2017/10/22  
 * update: 2017/10/22
 * version: 
*/
public interface BroadcastDetailContract {

    interface EmptyView {
        void hideEmptyLayout();

        void showErrorLayout(int errorType);
    }

    interface View extends BaseView<BroadcastDetailContract.Presenter> {

    }

    interface Presenter extends BasePresenter<BroadcastDetailContract.View> {
        void getDetail();
    }

    //團隊賽
    interface GroupPresenter extends Presenter {

    }

    // 個人賽
    interface SinglePresenter extends Presenter {

    }

    //普通廣播
    interface NormalPresenter extends Presenter {

    }
}
