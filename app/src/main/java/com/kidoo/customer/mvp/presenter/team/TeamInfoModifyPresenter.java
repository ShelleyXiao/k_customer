package com.kidoo.customer.mvp.presenter.team;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.mvp.contract.team.TeamInfoModifyContract;
import com.kidoo.customer.mvp.interactor.team.TeamInfoModifyInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-19
 * Time: 17:20
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamInfoModifyPresenter extends BasePresenterImpl<TeamInfoModifyContract.View> implements TeamInfoModifyContract.Presenter {

    @Inject
    public TeamInfoModifyInteractor mInteractor;

    @Inject
    public TeamInfoModifyPresenter() {
    }

    @Override
    public void doUpdateTeamInfo(String teamId, String teamName, String teamMsg, String teamLogo) {
        Disposable disposable = mInteractor.updateTeamInfoAction(teamId, teamName, teamMsg, teamLogo, new TeamInfoModifyContract.Interactor.UpdateTeamInfoCallbck() {
            @Override
            public void onSuccess() {
                mPresenterView.updateSuccess(true);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.updateSuccess(false);
            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQueryPicInfo() {
        Disposable disposable = mInteractor.queryPicInfoAction(new TeamInfoModifyContract.Interactor.GetTokenCallback() {
            @Override
            public void onSuccess(QNToken token) {
                mPresenterView.updateQNToken(token);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

        addDisposable(disposable);
    }
}
