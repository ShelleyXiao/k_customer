package com.kidoo.customer.mvp.presenter.team;

import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.bean.TeamDetailResult;
import com.kidoo.customer.mvp.contract.team.TeamDetailContract;
import com.kidoo.customer.mvp.interactor.team.TeamDetailInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-18
 * Time: 17:15
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamDetailPresenterImpl extends BasePresenterImpl<TeamDetailContract.View>
        implements TeamDetailContract.Presenter {

    @Inject
    public TeamDetailInteractor mInteractor;

    @Inject
    public TeamDetailPresenterImpl() {
    }

    @Override
    public void doQueryTeamDetail(final String teamId) {
        Disposable disposable = mInteractor.queryTeamListAction(teamId, new TeamDetailContract.Interactor.GetTeamDetailCallback() {
            @Override
            public void onSuccess(TeamDetailResult iteamBean) {
                mPresenterView.updateTeamDetail(iteamBean);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg);
            }
        });

        addDisposable(disposable);
    }
}
