package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

/**
 * Created by Shelley on 2017/10/15.
 */

public interface MyBroadcastContract {

    interface  View extends BaseView {
        void executeOnLoadDataError(String error);
    }

    interface Presenter extends BasePresenter<MyBroadcastContract.View> {
        void queryMyBroadcast(int customerId);

    }

}
