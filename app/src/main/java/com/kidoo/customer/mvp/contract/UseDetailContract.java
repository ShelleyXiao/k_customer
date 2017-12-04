package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

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

    }


    interface Presenter extends BasePresenter<UseDetailContract.View> {

        void doUpdateUserInfo(String customerId, String realName, String naickName, String email,
                              String portait, int sex, int brithday, String sign);

    }

    interface Interactor {

        void updateUserInfoAction(String customerId, String realName, String naickName, String email,
                                  String portait, String sex, String brithday, String sign, UpdateInfoCallbck callbck);

        interface UpdateInfoCallbck {
            void onSuccess();
            void onFailure(String msg);
        }
    }
}
