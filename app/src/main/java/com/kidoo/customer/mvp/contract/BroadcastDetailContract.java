package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.adapter.broadcastDetail.model.IBroadcastDetailModel;
import com.kidoo.customer.mvp.presenter.BaseListPresenter;
import com.kidoo.customer.mvp.view.BaseListView;

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

    interface View extends BaseListView<Presenter, IBroadcastDetailModel> {

    }

    interface Presenter extends BaseListPresenter {
    }
}
