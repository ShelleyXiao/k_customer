package com.kidoo.customer.mvp.presenter.user;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.mvp.contract.user.CreateCompetionContract;
import com.kidoo.customer.mvp.interactor.user.CraeteCompetionInteractor;
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


public class CreateCompetionPresenterImpl extends BasePresenterImpl<CreateCompetionContract.View> implements CreateCompetionContract.Presenter {

    @Inject
    public CraeteCompetionInteractor mInteractor;

    @Inject
    public CreateCompetionPresenterImpl() {
    }

    @Override
    public void createCompetion(String customId, String matchJson) {
        Disposable disposable = mInteractor.createCompetionAction(customId, matchJson, new CreateCompetionContract.Interactor.CreateCompetionCallbck() {
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
        Disposable disposable = mInteractor.queryPicInfoAction(new CreateCompetionContract.Interactor.GetTokenCallback() {
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
