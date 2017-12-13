package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-13
 * Time: 14:26
 * Company: zx
 * Description:
 * FIXME
 */


public interface ChannelCampaignContract {

    interface View extends BaseView {

        void updateUserInfo(AllChannelResultBean channelResultBean);

    }


    interface Presenter extends BasePresenter<ChannelCampaignContract.View> {

        void doQueryAllChannels();

    }

    interface Interactor {

        Disposable queryAllChannelsAction(ChannelCampaignContract.Interactor.GetAllChannelsCallback callback);

        interface GetAllChannelsCallback {
            void onSuccess(AllChannelResultBean result);
            void onFailure(String msg);
        }
    }
}
