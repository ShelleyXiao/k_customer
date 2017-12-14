package com.kidoo.customer.mvp.contract.channelCampaign;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.bean.MatchListResult;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

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

    enum Type {
        all, more
    }

    interface View extends BaseView {

        void updateChannelInfo(AllChannelResultBean channelResultBean);


        void updateMatch(List<MatchBean> datas, PageInfo info);

        void loadMoreContent(List<MatchBean> datas, PageInfo info);

        void showError(String msg, Type type);

    }


    interface Presenter extends BasePresenter<ChannelCampaignContract.View> {

        void doQueryAllChannels();

        void doQueryMatchs(int channelCID, int pageSize, int pageNum);

        void doQuery(int channelCID);

    }

    interface Interactor {

        Disposable queryAllChannelsAction(ChannelCampaignContract.Interactor.GetAllChannelsCallback callback);

        Disposable queryMoreMatchsAction(int channelCID, int pageSize, int pageNum, final GetMatchsCallback callback);

        Disposable queryMacths(int channelCID, final GetMatchsCallback callback);


        interface GetAllChannelsCallback {
            void onSuccess(AllChannelResultBean result);

            void onFailure(String msg);
        }

        interface GetMatchsCallback {
            void onSuccess(KidooApiResult<MatchListResult> result);

            void onFailure(String msg);
        }
    }
}
