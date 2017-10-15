package com.kidoo.customer.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.api.ComParamContact;
import com.kidoo.customer.api.http.HttpManager;
import com.kidoo.customer.api.http.callback.SimpleCallBack;
import com.kidoo.customer.api.http.exception.ApiException;
import com.kidoo.customer.mvp.model.InitData;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.EmptyLayout;
import com.kidoo.customer.widget.RecyclerRefreshLayout;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-10-13
 * Time: 10:55
 * Company: zx
 * Description:
 * FIXME
 */


public class MyBroadcastFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mBroadcastList;
    @BindView(R.id.fragment_content_empty)
    EmptyLayout mEmpty;
    @BindView(R.id.swiperefreshlayout)
    RecyclerRefreshLayout mRecyclerRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_broadcast;
    }


    @Override
    public void initWidget(View mRoot) {
    }

    @Override
    public void initData() {
        HttpManager.get(ComParamContact.QnToken.PATH)
                .params("appType", "2")
                .execute(new SimpleCallBack<InitData>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(InitData initData) {
                        LogUtils.w(initData.toString());
                    }
                });
    }

}
