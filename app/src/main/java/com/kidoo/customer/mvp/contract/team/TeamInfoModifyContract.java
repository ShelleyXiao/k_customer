package com.kidoo.customer.mvp.contract.team;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * description:
 * autour: ShaudXiao
 * date: 2017/12/2
 * update: 2017/12/2
 * version:
 */

public interface TeamInfoModifyContract {

    interface View extends BaseView {

        void updateSuccess(boolean sccuss);

        void updateQNToken(QNToken token);

    }


    interface Presenter extends BasePresenter<TeamInfoModifyContract.View> {

        void doUpdateTeamInfo(String teamId, String teamName, String teamMsg,
                              String teamLogo);

        void doQueryPicInfo();

    }

    interface Interactor {

        Disposable updateTeamInfoAction(String teamId, String teamName, String teamMsg,
                                        String teamLogo, UpdateTeamInfoCallbck callbck);

        Disposable queryPicInfoAction(GetTokenCallback callback);

        interface UpdateTeamInfoCallbck {
            void onSuccess();

            void onFailure(String msg);
        }

        interface GetTokenCallback {
            void onSuccess(QNToken token);

            void onFailure(String msg);
        }
    }
}
