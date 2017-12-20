package com.kidoo.customer.mvp.contract.channelCampaign;

import com.kidoo.customer.bean.EnrollSituationResult;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 10:11
 * Company: zx
 * Description:
 * FIXME
 */


public interface CompetionEnrollContract {

    interface View extends BaseView {

        void updateCompetionEnrollList(EnrollSituationResult result);

        void updateTeamsBaseInfo(List<TeamBean> datas);

        void showError(String msg);

    }



    interface Presenter extends BasePresenter<CompetionEnrollContract.View> {

        void doQueryCompetionEnroll(int matchID);

        void doQueryTeamsBaesInfo(String teamIds);
    }

    interface Interactor {


        Disposable queryCompetionEnrollAction(int matchID, final CompetionEnrollContract.Interactor.GetCompetionEnrollCallback callback);

        Disposable queryTeamsBaesInfoAction(String teamIds , final GetTeamBaseInfoCallback callback);

        interface GetCompetionEnrollCallback {
            void onSuccess(EnrollSituationResult result);

            void onFailure(String msg);
        }

        interface GetTeamBaseInfoCallback {
            void onSuccess(List<TeamBean> datas);

            void onFailure(String msg);
        }
    }

}
