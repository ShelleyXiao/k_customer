package com.kidoo.customer.mvp.contract.team;

import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

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


        void updateTeamDetail(TeamBean teamBean);

        void showError(String msg);

    }


    interface Presenter extends BasePresenter<TeamDetailContract.View> {


        void doQueryTeamDetail(String teamId);
    }

    interface Interactor {


        Disposable queryTeamListAction(String name, final TeamDetailContract.Interactor.GetTeamDetailCallback callback);


        interface GetTeamDetailCallback {
            void onSuccess(KidooApiResult<TeamBean> teamBean);

            void onFailure(String msg);
        }
    }

}
