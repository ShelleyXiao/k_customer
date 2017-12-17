package com.kidoo.customer.mvp.contract.team;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.bean.QueryTeamResult;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.channelCampaign.ChannelCampaignContract;
import com.kidoo.customer.mvp.contract.channelCampaign.CompetionDetailContract;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/12/16.
 */

public interface QueryTeamContract {

    interface View extends BaseView {

        void updateChannelInfo(AllChannelResultBean channelResultBean);

        void updateTeamList(List<TeamBean> teamBeans, PageInfo pageInfo);

        void showError(String msg);

    }


    interface Presenter extends BasePresenter<QueryTeamContract.View> {

        void doQueryAllChannels();

        void doQueryTeamList(String name, int channelCID, int pageNO, int pageSize);
    }

    interface Interactor {


        Disposable queryTeamListAction(String name, int channelCID, int pageNO, int pageSize, final QueryTeamContract.Interactor.GetTeamListCallback callback);


        Disposable queryAllChannelsAction(QueryTeamContract.Interactor.GetAllChannelsCallback callback);


        interface GetAllChannelsCallback {
            void onSuccess(AllChannelResultBean result);

            void onFailure(String msg);
        }


        interface GetTeamListCallback {
            void onSuccess(KidooApiResult<QueryTeamResult> result);

            void onFailure(String msg);
        }
    }
}
