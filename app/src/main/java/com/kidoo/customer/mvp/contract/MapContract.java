package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.AreanaBean;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-08
 * Time: 14:46
 * Company: zx
 * Description:
 * FIXME
 */


public interface MapContract {

    interface View extends BaseView {

        void updateUserInfo(AllChannelResultBean channelResultBean);

        void updateAreans(List<AreanaBean> datas);

    }


    interface Presenter extends BasePresenter<MapContract.View> {

        void doQueryAllChannels();

        void doQueryAreans(int channelCId);


    }

    interface Interactor {

        Disposable queryAllChannelsAction(MapContract.Interactor.GetAllChannelsCallback callback);

        Disposable QueryAreansAction(int channelCID, MapContract.Interactor.GetAreansCallback callback);



        interface GetAllChannelsCallback {
            void onSuccess(AllChannelResultBean result);
            void onFailure(String msg);
        }

        interface GetAreansCallback {
            void onSuccess(List<AreanaBean> result);
            void onFailure(String msg);
        }
    }

}
