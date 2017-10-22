package com.kidoo.customer.mvp.presenter;

import android.content.Context;

import com.kidoo.customer.mvp.contract.BroadcastDetailContract;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/10/22  
 * update: 2017/10/22
 * version: 
*/

public class BroadcastDetailPresenter implements BroadcastDetailContract.Presenter {

    private Context mContext;
    private BroadcastDetailContract.View mView;

    public BroadcastDetailPresenter(Context context, BroadcastDetailContract.View view) {
        attachView(view);
        mView.setPresenter(this);
    }

    @Override
    public void attachView(BroadcastDetailContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getDetail() {

    }
}
