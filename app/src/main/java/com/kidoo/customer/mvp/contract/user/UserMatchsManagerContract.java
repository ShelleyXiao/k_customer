package com.kidoo.customer.mvp.contract.user;

import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.bean.MyMatchsResult;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-13
 * Time: 14:26
 * Company: zx
 * Description:
 * FIXME
 */


public interface UserMatchsManagerContract {

    enum Type {
        all, more
    }

    interface View extends BaseView {

        void updateMatch(List<MatchBean> datas, PageInfo info);

        void loadMoreContent(List<MatchBean> datas, PageInfo info);

        void showError(String msg, UserMatchsManagerContract.Type type);

    }


    interface Presenter extends BasePresenter<UserMatchsManagerContract.View> {


        void doQueryMatchsManager(long customerId2Query, int pageSize, int pageNum);

        void doQuery(long customerId2Query);

    }

    interface Interactor {


        Disposable queryMoreMatchsManagerAction(long customerId2Query, int pageSize, int pageNum, final GetMatchsCallback callback);

        Disposable queryMacthsManager(long customerId2Query, final GetMatchsCallback callback);


        interface GetMatchsCallback {
            void onSuccess(KidooApiResult<MyMatchsResult> result);

            void onFailure(String msg);
        }
    }
}
