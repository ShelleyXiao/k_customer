package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.mvp.contract.UseDetailContract;
import com.kidoo.customer.mvp.interactor.UserDetailInteractor;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/** 
 * description: 
 * autour: ShaudXiao
 * date: 2017/12/2  
 * update: 2017/12/2
 * version: 
*/

public class UserDetailPresenterImpl extends BasePresenterImpl<UseDetailContract.View> implements UseDetailContract.Presenter {


    @Inject
    UserDetailInteractor mInteractor;

    @Inject
    public UserDetailPresenterImpl() {

    }


    @Override
    public void doUpdateUserInfo(String customerId, String realName,
                                 String naickName, String email, String portait,
                                 int sex, long brithday, String sign) {
        LogUtils.i("customerId: " + customerId
                +"  email: "+ email
                + " portait：" + portait
                + " realName：" + realName
                + " realName：" + sex
                + " sign：" + sign);

        String token = null;

        Disposable disposable = mInteractor.updateUserInfoAction(token, customerId, realName, naickName, email, portait, String.valueOf(sex), String.valueOf(brithday), sign, new UseDetailContract.Interactor.UpdateInfoCallbck() {
            @Override
            public void onSuccess() {
                mPresenterView.updateSuccess(true);
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.updateSuccess(false);
            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQueryPicInfo() {
        Disposable disposable = mInteractor.queryPicInfoAction(new UseDetailContract.Interactor.GetTokenCallback() {
            @Override
            public void onSuccess(QNToken token) {
                mPresenterView.updateQNToken(token);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

        addDisposable(disposable);
    }
}
