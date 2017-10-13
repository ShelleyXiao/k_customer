package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

/**
 * User: ShaudXiao
 * Date: 2017-10-12
 * Time: 19:31
 * Company: zx
 * Description:
 * FIXME
 */


public interface BroadcastContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        void queryAllChannels();

        void findNeighbours(int channelCId, long longitude, long latitude, int scale, int type);
    }

}
