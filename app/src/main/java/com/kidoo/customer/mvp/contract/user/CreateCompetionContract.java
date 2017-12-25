package com.kidoo.customer.mvp.contract.user;

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

public interface CreateCompetionContract {

    interface View extends BaseView {

        void updateSuccess(boolean sccuss);

        void updateQNToken(QNToken token);

    }


    interface Presenter extends BasePresenter<CreateCompetionContract.View> {

        void createCompetion(String customId, String matchJson);

        void doQueryPicInfo();

    }

    interface Interactor {

        Disposable createCompetionAction(String customId, String matchJson, final CreateCompetionCallbck callbck);

        Disposable queryPicInfoAction(GetTokenCallback callback);

        interface CreateCompetionCallbck {
            void onSuccess();

            void onFailure(String msg);
        }

        interface GetTokenCallback {
            void onSuccess(QNToken token);

            void onFailure(String msg);
        }
    }
}
