package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.AuthModel;
import com.kidoo.customer.api.token.RSAKey;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.CheckAllTokenBean;
import com.kidoo.customer.cipher.rsa.Base64Utils;
import com.kidoo.customer.cipher.rsa.RSAUtil;
import com.kidoo.customer.mvp.contract.InitDataContract;
import com.kidoo.customer.mvp.interactor.InitDataInteractor;
import com.kidoo.customer.utils.LogUtils;

import java.util.Date;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-11-28
 * Time: 11:08
 * Company: zx
 * Description:
 * FIXME
 */


public class InitDataPresenterImpl extends  BasePresenterImpl<InitDataContract.View> implements InitDataContract.Presenter {


    @Inject
    public InitDataInteractor mInteractor;


    @Inject
    public InitDataPresenterImpl() {}

    @Override
    public void getInitData(String phoneNumber) {

    }


}
