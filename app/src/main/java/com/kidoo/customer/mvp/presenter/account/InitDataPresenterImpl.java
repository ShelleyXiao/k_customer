package com.kidoo.customer.mvp.presenter.account;

import com.kidoo.customer.mvp.contract.InitDataContract;
import com.kidoo.customer.mvp.interactor.account.InitDataInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-11-28
 * Time: 11:08
 * Company: zx
 * Description:
 * FIXME
 */


public class InitDataPresenterImpl extends BasePresenterImpl<InitDataContract.View> implements InitDataContract.Presenter {


    @Inject
    public InitDataInteractor mInteractor;


    @Inject
    public InitDataPresenterImpl() {}



    @Override
    public void getInitData() {

    }
}
