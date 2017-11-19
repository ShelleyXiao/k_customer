package com.kidoo.customer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.adapter.GroupBroadcastDetailAdapter;
import com.kidoo.customer.adapter.broadcastDetail.model.IBroadcastDetailModel;
import com.kidoo.customer.mvp.contract.BroadcastDetailContract;
import com.kidoo.customer.mvp.presenter.BroadcastDetailPresenter;
import com.kidoo.customer.ui.base.adapter.BaseRecyclerAdapter;
import com.kidoo.customer.ui.base.fragment.BaseRecyclerFragment;

/**
 * User: ShaudXiao
 * Date: 2017-10-23
 * Time: 19:36
 * Company: zx
 * Description:
 * FIXME
 */


public class GroupBroadcastDetilaFragment extends BaseRecyclerFragment<BroadcastDetailContract.Presenter, IBroadcastDetailModel>
        implements BroadcastDetailContract.View {

    public static final String BUNDLE_KEY_BROADCAST_ID = "broadcastId";

    private int mBroadcastId;

    public static Fragment instantiate(Context context, int broadcastId) {
        Fragment fragment = new GroupBroadcastDetilaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_BROADCAST_ID, broadcastId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public GroupBroadcastDetilaFragment() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void initBundle(Bundle bundle) {
        if(bundle != null) {
            mBroadcastId = bundle.getInt(BUNDLE_KEY_BROADCAST_ID);
        }
    }

    @Override
    public void initData() {
        mPresenter = new BroadcastDetailPresenter(getActivity(), this, (int)AccountHelper.getUserId(), mBroadcastId);
    }
//
//    @Override
//    public void setPresenter(BroadcastDetailContract.Presenter presenter) {
//        mPresenter = presenter;
//    }

    @Override
    protected BaseRecyclerAdapter<IBroadcastDetailModel> getAdapter() {
        return new GroupBroadcastDetailAdapter(getActivity(), BaseRecyclerAdapter.NEITHER);
    }

    @Override
    protected void onItemClick(IBroadcastDetailModel iBroadcastDetailModel, int position) {

    }
}
