package com.kidoo.customer.mvp.presenter.user;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.mvp.contract.user.CompetionAddNodeContract;
import com.kidoo.customer.mvp.interactor.user.CompetionAddNodeInteractor;
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


public class CompetionAddNodePresenterImpl extends BasePresenterImpl<CompetionAddNodeContract.View> implements CompetionAddNodeContract.Presenter {

    @Inject
    public CompetionAddNodeInteractor mInteractor;

    @Inject
    public CompetionAddNodePresenterImpl() {
    }

    @Override
    public void addCompetionNode(String matchId, String msg, String time, String showType, String pic, String picMini) {
        Disposable disposable = mInteractor.addCompetionNodeAction(matchId,  msg, time,showType, pic, picMini, new CompetionAddNodeContract.Interactor.CompetionAddNodeCallbck() {
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
        Disposable disposable = mInteractor.queryPicInfoAction(new CompetionAddNodeContract.Interactor.GetTokenCallback() {
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
