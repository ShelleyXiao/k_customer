package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.mvp.contract.UseDetailContract;

import javax.inject.Inject;

/** 
 * description: 
 * autour: ShaudXiao
 * date: 2017/12/2  
 * update: 2017/12/2
 * version: 
*/

public class UserDetailPresenterImpl extends BasePresenterImpl<UseDetailContract.View> implements UseDetailContract.Presenter {



    @Inject
    public UserDetailPresenterImpl() {

    }


    @Override
    public void doUpdateUserInfo(String customerId, String realName, String naickName, String email, String portait, int sex, int brithday, String sign) {

    }
}
