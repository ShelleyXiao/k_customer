package com.kidoo.customer.mvp.contract.channelCampaign;

import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 10:11
 * Company: zx
 * Description:
 * FIXME
 */


public interface CompetionEnrollActionContract {


    enum MsgType {
        register, quit
    }

    interface View extends BaseView {

        void actionSuccess(MsgType type);

        void showError(String msg, MsgType type);

    }


    interface Presenter extends BasePresenter<CompetionEnrollActionContract.View> {


        void doQuitCompetionEnroll(int matchId);

        void doCompetionEnroll(int matchId);
    }

    interface Interactor {


        Disposable quitCompetionEnrollAction(int matchId, final CompetionEnrollActionCallback callback);

        Disposable competionEnrollAction(int matchId, final CompetionEnrollActionCallback callback);


        interface CompetionEnrollActionCallback {
            void onSuccess();

            void onFailure(String msg);
        }
    }

}
