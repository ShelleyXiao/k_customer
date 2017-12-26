package com.kidoo.customer.mvp.presenter.user;

import com.kidoo.customer.bean.QueryTeamResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.user.UserTeamsContract;
import com.kidoo.customer.mvp.interactor.user.UserTeamsInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by ShaudXiao on 2017/12/17.
 */

public class UsersTeamsListPresenterImpl extends BasePresenterImpl<UserTeamsContract.View> implements UserTeamsContract.Presenter {

    @Inject
    public UserTeamsInteractor mInteractor;

    @Inject
    public UsersTeamsListPresenterImpl() {

    }


    @Override
    public void doQueryTeamList(String customId, int pageNO, int pageSize) {
        Disposable disposable = mInteractor.queryTeamListAction(customId,  pageNO, pageSize, new UserTeamsContract.Interactor.GetTeamListCallback() {
            @Override
            public void onSuccess(KidooApiResult<QueryTeamResult> result) {
                mPresenterView.updateTeamList(result.getData().getTeamList(), result.getPageInfo());
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg);
            }
        });

        addDisposable(disposable);
    }
}
