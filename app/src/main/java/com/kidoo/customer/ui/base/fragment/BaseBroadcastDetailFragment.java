package com.kidoo.customer.ui.base.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.mvp.contract.BroadcastDetailContract;
import com.kidoo.customer.widget.EmptyLayout;
import com.kidoo.customer.widget.RecyclerRefreshLayout;

import butterknife.BindView;

/**
 * description: 廣播詳情實體
 * autour: ShaudXiao
 * date: 2017/10/22
 * update: 2017/10/22
 * version:
 */

public abstract class BaseBroadcastDetailFragment extends BaseFragment implements BroadcastDetailContract.View
        , BroadcastDetailContract.EmptyView {


    @BindView(R.id.recyclerView)
    RecyclerView mDataoList;

    @BindView(R.id.fragment_content_empty)
    EmptyLayout mEmptyLayout;

    @BindView(R.id.swiperefreshlayout)
    RecyclerRefreshLayout mRefreshLayout;

    protected BroadcastDetailContract.Presenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_broadcast_detail;
    }

    @Override
    public void initWidget(View rootView) {
        super.initWidget(rootView);

        mPresenter = getPresenter();

        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmptyLayout.getErrorState() != EmptyLayout.NETWORK_LOADING) {
                    mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
//                    mPresenter.getDetail();
                }
            }
        });
    }

    @Override
    public void setPresenter(BroadcastDetailContract.Presenter presenter) {
        this.mPresenter =  presenter;
    }

    @Override
    public void showNetworkError(String str) {

    }


    @Override
    public void hideEmptyLayout() {
        mEmptyLayout.setErrorType(EmptyLayout. HIDE_LAYOUT);
    }

    @Override
    public void showErrorLayout(int errorType) {
        mEmptyLayout.setErrorType(errorType);
        mRefreshLayout.setVisibility(View.GONE);
    }

    protected abstract BroadcastDetailContract.Presenter getPresenter();
}
