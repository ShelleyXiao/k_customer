package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.bean.Broadcast;
import com.kidoo.customer.mvp.presenter.BaseListPresenter;
import com.kidoo.customer.mvp.view.BaseListView;

/**
 * Created by Shelley on 2017/10/15.
 */

public interface MyBroadcastContract {

    interface  View extends BaseListView<MyBroadcastContract.Presenter, Broadcast> {

        void updateSendBroadcastView(int status);
    }

    interface Presenter extends BaseListPresenter {
//        void queryMyBroadcast(int customerId);
    }

}
