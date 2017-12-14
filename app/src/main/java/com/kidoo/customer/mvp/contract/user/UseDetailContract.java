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

public interface UseDetailContract {

    interface View extends BaseView {

        void updateSuccess(boolean sccuss);

        void updateQNToken(QNToken token);

    }


    interface Presenter extends BasePresenter<UseDetailContract.View> {

        void doUpdateUserInfo(String customerId, String realName, String naickName, String email,
                              String portait, int sex, long brithday, String sign);

        void doQueryPicInfo();

    }

    interface Interactor {

        Disposable updateUserInfoAction(String token, String customerId, String realName, String naickName, String email,
                                        String portait, String sex, String brithday, String sign, UpdateInfoCallbck callbck);

        Disposable queryPicInfoAction(GetTokenCallback callback);

        interface UpdateInfoCallbck {
            void onSuccess();
            void onFailure(String msg);
        }

        interface GetTokenCallback {
            void onSuccess(QNToken token);
            void onFailure(String msg);
        }
    }
}
