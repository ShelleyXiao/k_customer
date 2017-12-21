package com.kidoo.customer.mvp.interactor.user;

import com.kidoo.customer.bean.MatchListResult;
import com.kidoo.customer.bean.MyMatchsResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.channelCampaign.ChannelCampaignContract;
import com.kidoo.customer.mvp.contract.user.UserMatchsContract;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-21
 * Time: 14:59
 * Company: zx
 * Description:
 * FIXME
 */


public class UserMatchsPresenterImpl extends BasePresenterImpl<UserMatchsContract.View>
        implements UserMatchsContract.Presenter {


    @Inject
    public UserMatchsInteractor mInteractor;


    @Inject
    public UserMatchsPresenterImpl() {


    }

    @Override
    public void doQueryMatchs(long customerId2Query, int pageSize, int pageNo) {
        Disposable disposable = mInteractor.queryMoreMatchsAction(customerId2Query, pageSize, pageNo, new UserMatchsContract.Interactor.GetMatchsCallback() {
            @Override
            public void onSuccess(KidooApiResult<MyMatchsResult> result) {
                mPresenterView.loadMoreContent(result.getData().getMatchList(), result.getPageInfo());
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg, UserMatchsContract.Type.more);
            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQuery(long customerId2Query) {
        Disposable disposable = mInteractor.queryMacths(customerId2Query, new UserMatchsContract.Interactor.GetMatchsCallback() {
            @Override
            public void onSuccess(KidooApiResult<MyMatchsResult> result) {
//                LogUtils.i(result.toString());
                if (result.getData() != null) {
                    mPresenterView.updateMatch(result.getData().getMatchList(), result.getPageInfo());
                } else {
                    mPresenterView.updateMatch(null, null);
                }
            }

            @Override
            public void onFailure(String msg) {
                LogUtils.e(msg);
                mPresenterView.showError(msg, UserMatchsContract.Type.all);
            }
        });

        addDisposable(disposable);
    }


}
