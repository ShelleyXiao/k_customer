package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.mvp.contract.WondefulEventNewsContract;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-12-12
 * Time: 20:13
 * Company: zx
 * Description:
 * FIXME
 */


public class WondfulEventNewsPresenterImpl extends BasePresenterImpl<WondefulEventNewsContract.View>
        implements WondefulEventNewsContract.Presenter{

    @Inject
    public WondfulEventNewsPresenterImpl() {

    }

    @Override
    public void doQueryNews(int type, int pageSize, int pageNum) {

    }

    @Override
    public void doQueryList(int type) {

    }
}
