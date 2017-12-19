package com.kidoo.customer.mvp.contract.team;

import com.kidoo.customer.bean.Customer;
import com.kidoo.customer.bean.TeamDetailResult;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-18
 * Time: 17:10
 * Company: zx
 * Description:
 * FIXME
 */


public interface TeamDetailContract {

    interface View extends BaseView {


        void updateTeamDetail(TeamDetailResult teamBean);

        void updateMemberInfo(List<Customer> memberInfos);

        void showError(String msg);

        void joinTeamResult(boolean isSuccess, String errorMsg);

        void quitTeamResult(boolean isSuccess, String errorMsg);

    }


    interface Presenter extends BasePresenter<TeamDetailContract.View> {


        void doQueryTeamDetail(String teamId);

        void doQueryMemberInfo(String ids);

        void doJoinTeam(String teamID);

        void doQuitTeam(String teamId);
    }

    interface Interactor {


        Disposable queryTeamListAction(String name, final TeamDetailContract.Interactor.GetTeamDetailCallback callback);

        Disposable queryMemebersInfoAction(String ids, final TeamDetailContract.Interactor.GetMemberInfoCallback callback);

        Disposable joinTeamAction(String teamID, final JoinOrQuitCallback callback);

        Disposable quitTeamAction(String teamID, final JoinOrQuitCallback callback);


        interface GetTeamDetailCallback {
            void onSuccess(TeamDetailResult iteamBean);

            void onFailure(String msg);
        }

        interface GetMemberInfoCallback {
            void onSuccess(List<Customer> customers);

            void onFailure(String msg);
        }

        interface JoinOrQuitCallback {
            void onSuccess();

            void onFailure(String msg);
        }
    }

}
