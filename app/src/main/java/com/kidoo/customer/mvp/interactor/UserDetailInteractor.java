package com.kidoo.customer.mvp.interactor;

import com.kidoo.customer.api.UpdateUserInfoApi;
import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.UseDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * User: ShaudXiao
 * Date: 2017-12-04
 * Time: 18:25
 * Company: zx
 * Description:
 * FIXME
 */


public class UserDetailInteractor implements UseDetailContract.Interactor {

    @Inject
    public UserDetailInteractor() {

    }

    @Override
    public void updateUserInfoAction(String customerId, String realName, String nickName, String email, String portait, String sex, String brithday, String sign, UpdateInfoCallbck callbck) {
        Observable<KidooApiResult<ReturnNullBean>> observable = UpdateUserInfoApi.updateUserInfoApi(
                customerId, realName, nickName, email, portait, sex, brithday, sign
        );



    }
}
