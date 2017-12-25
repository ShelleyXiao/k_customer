package com.kidoo.customer.mvp.contract.channelCampaign;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 14:55
 * Company: zx
 * Description:
 * FIXME
 */


public interface CompetionDetailContract {

    interface View extends BaseView {

        void updateCompetionDetail(CompetionDetailResult result);

        void showError(String msg);

        void updateQNToken(QNToken token);

    }


    interface Presenter extends BasePresenter<CompetionDetailContract.View> {

        void doQueryCompetionDetail(int matchID);

        void doQueryPicInfo();
    }

    interface Interactor {


        Disposable queryCompetionDetailAction(int matchID, final CompetionDetailContract.Interactor.GetCompetionDetailCallback callback);


        interface GetCompetionDetailCallback {
            void onSuccess(CompetionDetailResult result);

            void onFailure(String msg);
        }

        Disposable queryPicInfoAction(final CompetionDetailContract.Interactor.GetTokenCallback callback);


        interface GetTokenCallback {
            void onSuccess(QNToken token);

            void onFailure(String msg);
        }
    }

}
