package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.mvp.contract.MapContract;
import com.kidoo.customer.mvp.interactor.MapInteractor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-08
 * Time: 14:50
 * Company: zx
 * Description:
 * FIXME
 */


public class MapPresenterImpl extends BasePresenterImpl<MapContract.View> implements MapContract.Presenter {


    @Inject
    public MapInteractor mInteractor;

    @Inject
    public MapPresenterImpl() {
    }

    @Override
    public void doQueryAllChannels() {
        Disposable disposable = mInteractor.queryAllChannelsAction(new MapContract.Interactor.GetAllChannelsCallback() {
            @Override
            public void onSuccess(AllChannelResultBean result) {
                mPresenterView.updateUserInfo(result);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

        addDisposable(disposable);
    }
}
