package com.kidoo.customer.mvp.contract.user;

import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.bean.QueryTeamResult;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/12/16.
 */

public interface UserTeamsContract {

    interface View extends BaseView {


        void updateTeamList(List<TeamBean> teamBeans, PageInfo pageInfo);

        void showError(String msg);

    }


    interface Presenter extends BasePresenter<UserTeamsContract.View> {


        void doQueryTeamList(String name, int pageNO, int pageSize);
    }

    interface Interactor {


        Disposable queryTeamListAction(String name, int pageNO, int pageSize, final UserTeamsContract.Interactor.GetTeamListCallback callback);


        interface GetTeamListCallback {
            void onSuccess(KidooApiResult<QueryTeamResult> result);

            void onFailure(String msg);
        }
    }
}
