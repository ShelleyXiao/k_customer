package com.kidoo.customer.mvp.presenter;

import android.content.Context;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.ComParamContact;
import com.kidoo.customer.api.http.HttpManager;
import com.kidoo.customer.api.http.callback.SimpleCallBack;
import com.kidoo.customer.api.http.exception.ApiException;
import com.kidoo.customer.mvp.contract.MyBroadcastContract;
import com.kidoo.customer.mvp.model.MyBroadcastResult;
import com.kidoo.customer.mvp.view.BaseView;
import com.kidoo.customer.utils.LogUtils;

/**
 * User: ShaudXiao
 * Date: 2017-10-16
 * Time: 13:58
 * Company: zx
 * Description:
 * FIXME
 */


public class MyBroadcastPresenter implements MyBroadcastContract.Presenter {

    private Context mContext;
    private MyBroadcastContract.View mView;
    private String customerId;
    private int pageNum = 0;
    private int mMaxPage = 0;

    public MyBroadcastPresenter(Context context, MyBroadcastContract.View view, String customerId) {
        this.mContext = context;
        attachView(view);
        this.mView.setPresenter(this);
        this.customerId = customerId;
    }

    /*
    * 刷新
    * */
    @Override
    public void onRefreshing() {
        pageNum = 0;
        queryMyBroadcast(customerId, pageNum);
    }

    /*
    * 加载更多
    * */
    @Override
    public void onLoadMore() {

    }

    @Override
    public void attachView(BaseView view) {
        this.mView = (MyBroadcastContract.View) view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    private void queryMyBroadcast(String customerId, int pageNum) {

        HttpManager.get(ComParamContact.MyBroadcast.PATH)
                .params(ComParamContact.MyBroadcast.USER_ID, AccountHelper.getUserId() + "")
                .execute(new SimpleCallBack<MyBroadcastResult>() {

                    @Override
                    public void onError(ApiException e) {
                        mView.showNetworkError(e.getMessage());
                    }

                    @Override
                    public void onSuccess(MyBroadcastResult myBroadcastResult) {
                        if (mView != null) {
                            mView.updateSendBroadcastView(myBroadcastResult.getMyBroadcastStatus());
                        }

                        mView.onRefreshSuccess(myBroadcastResult.getBroadcastList());
                        if (myBroadcastResult.getPageInfo() == null
                                || myBroadcastResult.getBroadcastList().size() < myBroadcastResult.getPageInfo().getPageSize()) {
                            mView.showMoreMore();
                        }
                        LogUtils.w(myBroadcastResult.getBroadcastList().get(0).toString());
                        mView.onComplete();
//                        LogUtils.w(myBroadcastResult.getPageInfo().toString());
                    }
                });
    }
}
