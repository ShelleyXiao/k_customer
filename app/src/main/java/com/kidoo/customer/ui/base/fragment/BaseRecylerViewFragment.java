package com.kidoo.customer.ui.base.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.adapter.BaseGeneralRecyclerAdapter;
import com.kidoo.customer.ui.base.adapter.BaseRecyclerAdapter;
import com.kidoo.customer.utils.TDevice;
import com.kidoo.customer.widget.EmptyLayout;
import com.kidoo.customer.widget.RecyclerRefreshLayout;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-23
 * Time: 19:26
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseRecylerViewFragment<T> extends BaseFragment implements
        RecyclerRefreshLayout.SuperRefreshLayoutListener,
        BaseRecyclerAdapter.OnItemClickListener,
        View.OnClickListener,
        BaseGeneralRecyclerAdapter.Callback {
    private final String TAG = this.getClass().getSimpleName();
    protected BaseRecyclerAdapter<T> mAdapter;
    protected RecyclerView mRecyclerView;
    protected RecyclerRefreshLayout mRefreshLayout;
    protected boolean isRefreshing;
    protected EmptyLayout mErrorLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view;
    }

    @Override
    protected void initWidget(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRefreshLayout = (RecyclerRefreshLayout) root.findViewById(R.id.refreshLayout);
        mErrorLayout = (EmptyLayout) root.findViewById(R.id.error_layout);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initData() {
        mAdapter = getRecyclerAdapter();
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mErrorLayout.setOnLayoutClickListener(this);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState && getActivity() != null
                        && getActivity().getCurrentFocus() != null) {
                    TDevice.hideSoftKeyboard(getActivity().getCurrentFocus());
                }
            }
        });
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);


        boolean isNeedEmptyView = isNeedEmptyView();
        if (isNeedEmptyView) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            mRefreshLayout.setVisibility(View.GONE);

            //if is the first loading

            onRefreshing();
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRoot.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    onRefreshing();
                }
            });
        }


    }

    @Override
    public void onClick(View v) {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefreshing();
    }


    @Override
    public void onItemClick(int position, long itemId) {

    }

    @Override
    public void onRefreshing() {
        isRefreshing = true;
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);
        requestData();
    }

    @Override
    public void onLoadMore() {
        mAdapter.setState(isRefreshing ? BaseRecyclerAdapter.STATE_HIDE : BaseRecyclerAdapter.STATE_LOADING, true);
        requestData();
    }

    protected void requestData() {
    }

    protected void onRequestSuccess(int code) {

    }

    protected void onRequestFinish() {
        onComplete();
    }

    protected void onRequestError() {
        onComplete();
        if (mAdapter.getItems().size() == 0) {
            if (isNeedEmptyView()) mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        }
    }

    protected void onComplete() {
        mRefreshLayout.onComplete();
        isRefreshing = false;
    }

    protected void setListData(List<T> resultBean) {
        if (isRefreshing) {
//            AppConfig.getAppConfig(getActivity()).set("system_time", resultBean.getTime());
            mAdapter.clear();
            mAdapter.addAll(resultBean);

        } else {
            mAdapter.addAll(resultBean);
        }

//        if (resultBean.getResult().getItems() == null
//                || resultBean.getResult().getItems().size() < 20)
//            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);

        if (mAdapter.getItems().size() > 0) {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mErrorLayout.setErrorType(
                    isNeedEmptyView()
                            ? EmptyLayout.NODATA
                            : EmptyLayout.HIDE_LAYOUT);
        }
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected abstract BaseRecyclerAdapter<T> getRecyclerAdapter();

    protected abstract Type getType();

    /**
     * 获取缓存bean的class
     */
    protected Class<T> getCacheClass() {
        return null;
    }

    @Override
    public Date getSystemTime() {
        return new Date();
    }

    /**
     * 需要缓存
     *
     * @return isNeedCache
     */
    protected boolean isNeedCache() {
        return true;
    }

    /**
     * 需要空的View
     *
     * @return isNeedEmptyView
     */
    protected boolean isNeedEmptyView() {
        return true;
    }
}
