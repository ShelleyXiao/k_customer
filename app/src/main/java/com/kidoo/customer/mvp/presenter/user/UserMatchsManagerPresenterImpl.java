package com.kidoo.customer.mvp.presenter.user;

import com.kidoo.customer.bean.MyMatchsResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.user.UserMatchsManagerContract;
import com.kidoo.customer.mvp.interactor.user.UserMatchsManagerInteractor;
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


public class UserMatchsManagerPresenterImpl extends BasePresenterImpl<UserMatchsManagerContract.View>
        implements UserMatchsManagerContract.Presenter {


    @Inject
    public UserMatchsManagerInteractor mInteractor;


    @Inject
    public UserMatchsManagerPresenterImpl() {


    }

    @Override
    public void doQueryMatchsManager(long customerId2Query, int pageSize, int pageNo) {
        Disposable disposable = mInteractor.queryMoreMatchsManagerAction(customerId2Query, pageSize, pageNo, new UserMatchsManagerContract.Interactor.GetMatchsCallback() {
            @Override
            public void onSuccess(KidooApiResult<MyMatchsResult> result) {
                mPresenterView.loadMoreContent(result.getData().getMatchList(), result.getPageInfo());
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg, UserMatchsManagerContract.Type.more);
            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQuery(long customerId2Query) {
        Disposable disposable = mInteractor.queryMacthsManager(customerId2Query, new UserMatchsManagerContract.Interactor.GetMatchsCallback() {
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
                mPresenterView.showError(msg, UserMatchsManagerContract.Type.all);
            }
        });

        addDisposable(disposable);
    }


}
