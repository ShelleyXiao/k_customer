package com.kidoo.customer.mvp.presenter.channelCampaign;

import com.kidoo.customer.mvp.contract.channelCampaign.CompetionEnrollActionContract;
import com.kidoo.customer.mvp.interactor.channelCampaign.CompetionEnrollActionInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 11:22
 * Company: zx
 * Description: 退出、加入比赛
 * FIXME
 */


public class CompetionEnrollActionPresenterImpl extends BasePresenterImpl<CompetionEnrollActionContract.View>
        implements CompetionEnrollActionContract.Presenter {

    @Inject
    public CompetionEnrollActionInteractor mInteractor;

    @Inject
    public CompetionEnrollActionPresenterImpl() {

    }

    @Override
    public void doQuitCompetionEnroll(int matchId) {
        Disposable disposable = mInteractor.quitCompetionEnrollAction(matchId, new CompetionEnrollActionContract.Interactor.CompetionEnrollActionCallback() {
            @Override
            public void onSuccess() {
                mPresenterView.actionSuccess(CompetionEnrollActionContract.MsgType.quit);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg, CompetionEnrollActionContract.MsgType.quit);
            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doCompetionEnroll(int matchId) {
        Disposable disposable = mInteractor.competionEnrollAction(matchId, new CompetionEnrollActionContract.Interactor.CompetionEnrollActionCallback() {
            @Override
            public void onSuccess() {
                mPresenterView.actionSuccess(CompetionEnrollActionContract.MsgType.register);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg, CompetionEnrollActionContract.MsgType.register);
            }
        });

        addDisposable(disposable);
    }
}
