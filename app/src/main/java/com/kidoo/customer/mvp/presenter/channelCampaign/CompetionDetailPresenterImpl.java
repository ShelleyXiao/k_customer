package com.kidoo.customer.mvp.presenter.channelCampaign;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.mvp.contract.channelCampaign.CompetionDetailContract;
import com.kidoo.customer.mvp.interactor.channelCampaign.CompetionDetailInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 15:06
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionDetailPresenterImpl extends BasePresenterImpl<CompetionDetailContract.View>
        implements CompetionDetailContract.Presenter {


    @Inject
    public CompetionDetailInteractor mInteractor;

    @Inject
    public CompetionDetailPresenterImpl() {

    }

    @Override
    public void doQueryCompetionDetail(int matchID) {
        if (matchID < 0) {
            mPresenterView.showError("");
            return;
        }
        Disposable disposable = mInteractor.queryCompetionDetailAction(matchID, new CompetionDetailContract.Interactor.GetCompetionDetailCallback() {
            @Override
            public void onSuccess(CompetionDetailResult result) {
                mPresenterView.updateCompetionDetail(result);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg);
            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQueryPicInfo() {
        Disposable disposable = mInteractor.queryPicInfoAction(new CompetionDetailContract.Interactor.GetTokenCallback() {
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
