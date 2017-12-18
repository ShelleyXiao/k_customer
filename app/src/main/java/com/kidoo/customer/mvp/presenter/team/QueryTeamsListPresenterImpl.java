package com.kidoo.customer.mvp.presenter.team;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.QueryTeamResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.team.QueryTeamContract;
import com.kidoo.customer.mvp.interactor.team.QueryTeamInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by ShaudXiao on 2017/12/17.
 */

public class QueryTeamsListPresenterImpl extends BasePresenterImpl<QueryTeamContract.View> implements QueryTeamContract.Presenter {

    @Inject
    public QueryTeamInteractor mInteractor;

    @Inject
    public QueryTeamsListPresenterImpl() {

    }

    @Override
    public void doQueryAllChannels() {
        Disposable disposable = mInteractor.queryAllChannelsAction(new QueryTeamContract.Interactor.GetAllChannelsCallback() {
            @Override
            public void onSuccess(AllChannelResultBean result) {
//                LogUtils.i(result.getChannelAList().size());
                mPresenterView.updateChannelInfo(result);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQueryTeamList(String name, int channelCID, int pageNO, int pageSize) {
        Disposable disposable = mInteractor.queryTeamListAction(name, channelCID, pageNO, pageSize, new QueryTeamContract.Interactor.GetTeamListCallback() {
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
