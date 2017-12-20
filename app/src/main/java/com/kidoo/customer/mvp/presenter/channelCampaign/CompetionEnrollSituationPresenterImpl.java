package com.kidoo.customer.mvp.presenter.channelCampaign;

import com.kidoo.customer.bean.EnrollSituationResult;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.mvp.contract.channelCampaign.CompetionEnrollContract;
import com.kidoo.customer.mvp.interactor.channelCampaign.CompetionEnrollSituationInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 10:39
 * Company: zx
 * Description: 报名情况
 * FIXME
 */


public class CompetionEnrollSituationPresenterImpl extends BasePresenterImpl<CompetionEnrollContract.View>
        implements CompetionEnrollContract.Presenter {


    @Inject
    public CompetionEnrollSituationInteractor mInteractor;

    @Inject
    public CompetionEnrollSituationPresenterImpl() {
    }

    @Override
    public void doQueryCompetionEnroll(int matchID) {
        Disposable disposable = mInteractor.queryCompetionEnrollAction(matchID, new CompetionEnrollContract.Interactor.GetCompetionEnrollCallback() {
            @Override
            public void onSuccess(EnrollSituationResult result) {
                mPresenterView.updateCompetionEnrollList(result);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg);
            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQueryTeamsBaesInfo(String teamIds) {
        Disposable disposable = mInteractor.queryTeamsBaesInfoAction(teamIds, new CompetionEnrollContract.Interactor.GetTeamBaseInfoCallback() {
            @Override
            public void onSuccess(List<TeamBean> datas) {
                mPresenterView.updateTeamsBaseInfo(datas);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg);
            }
        });

        addDisposable(disposable);
    }
}
