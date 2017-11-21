package com.kidoo.customer.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.MybroadcastAdapter;
import com.kidoo.customer.adapter.itemDecoration.DividerItemDecoration;
import com.kidoo.customer.mvp.contract.MyBroadcastContract;
import com.kidoo.customer.bean.Broadcast;
import com.kidoo.customer.mvp.presenter.MyBroadcastPresenter;
import com.kidoo.customer.ui.base.adapter.BaseRecyclerAdapter;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.TDevice;
import com.kidoo.customer.widget.EmptyLayout;
import com.kidoo.customer.widget.RecyclerRefreshLayout;
import com.kidoo.customer.widget.dialog.CommonDialog;

import java.util.List;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-10-13
 * Time: 10:55
 * Company: zx
 * Description:
 * FIXME
 */


public class MyBroadcastFragment extends BaseFragment implements
         RecyclerRefreshLayout.SuperRefreshLayoutListener
        , MyBroadcastContract.View, View.OnClickListener {

    @BindView(R.id.send)
    ViewGroup mSendBroadcast;

    @BindView(R.id.send_broadcast)
    ImageView mSendBroadcastBtn;

    @BindView(R.id.recyclerView)
    RecyclerView mBroadcastList;

    @BindView(R.id.fragment_content_empty)
    EmptyLayout mEmpty;

    @BindView(R.id.swiperefreshlayout)
    RecyclerRefreshLayout mRefreshLayout;

    private MyBroadcastPresenter mPresenter;
    private MybroadcastAdapter mMybroadcastAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_broadcast;
    }


    @Override
    public void initWidget(View mRoot) {
        mMybroadcastAdapter = new MybroadcastAdapter(getActivity(), BaseRecyclerAdapter.NEITHER);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mBroadcastList.setLayoutManager(new LinearLayoutManager(mContext));
        mBroadcastList.setAdapter(mMybroadcastAdapter);

//        mBroadcastList.addItemDecoration(new KidooRlvItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL, KidooRlvItemDecoration.ONLY_FOOTER));
        mBroadcastList.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mSendBroadcastBtn.setOnClickListener(this);
        mEmpty.setOnLayoutClickListener(this);
        mPresenter = new MyBroadcastPresenter(getActivity(), this, AccountHelper.getUserId() + "");

    }

    @Override
    public void initData() {
        if (!TDevice.hasInternet()) {
            mEmpty.setVisibility(View.VISIBLE);
            mEmpty.setErrorType(EmptyLayout.NETWORK_ERROR);
            return;
        }
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                if (mPresenter == null)
                    return;
                mPresenter.onRefreshing();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onRefreshing() {
        LogUtils.w("onRefreshing");
        if (mPresenter == null)
            return;
        mMybroadcastAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);
        mPresenter.onRefreshing();
    }

    @Override
    public void onLoadMore() {
        LogUtils.w("onLoadMore");
        mPresenter.onLoadMore();
        mMybroadcastAdapter.setState(BaseRecyclerAdapter.STATE_LOADING, true);
    }

//    @Override
//    public void setPresenter(MyBroadcastContract.Presenter presenter) {
//        this.mPresenter = (MyBroadcastPresenter) presenter;
//    }

    public void showNetworkError(String str) {
        mEmpty.setErrorType(EmptyLayout.NETWORK_ERROR);
        mEmpty.setErrorMessage(str);
    }

    @Override
    public void onRefreshSuccess(List<Broadcast> data) {
//        LogUtils.w("onRefreshSuccess " + data.size());
        mMybroadcastAdapter.resetItem(data);
    }

    @Override
    public void onLoadMoreSuccess(List<Broadcast> data) {
        LogUtils.w("onLoadMoreSuccess");
        mMybroadcastAdapter.addAll(data);

    }

    @Override
    public void showMoreMore() {
        LogUtils.w("showMoreMore");
    }

    @Override
    public void onComplete() {
        mRefreshLayout.onComplete();
    }

    @Override
    public void updateSendBroadcastView(int status) {
        //0没有加入广播 1只加入普通广播 2只加入比赛广播 3加入比赛和普通广播
//        if(status == 1 || status == 3) {
//            mSendBroadcast.setVisibility(View.GONE);
//        } else {
//            mSendBroadcast.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_content_empty:
                if (!TDevice.hasInternet()) {
                    CommonDialog dialog = DialogHelper.getNetworkErrorDialog(getActivity());
                    dialog.show();
                    return;
                }
                mEmpty.setErrorType(EmptyLayout.NETWORK_LOADING);
                onRefreshing();
                break;
            case R.id.send_broadcast:

                break;
            default:
                break;
        }
    }
}
