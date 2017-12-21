package com.kidoo.customer.ui.activity.channelCampaign;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.competion.CompetionEnrollSituationAdapter;
import com.kidoo.customer.bean.CompetionEnrollbean;
import com.kidoo.customer.bean.EnrollSituationResult;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.mvp.contract.channelCampaign.CompetionEnrollContract;
import com.kidoo.customer.mvp.presenter.channelCampaign.CompetionEnrollSituationPresenterImpl;
import com.kidoo.customer.ui.activity.team.TeamDetailActivity;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.NetWorkUtil;
import com.kidoo.customer.widget.recylerview.LoadMoreFooterView;
import com.kidoo.customer.widget.recylerview.RefreshHeaderView;
import com.kidoo.customer.widget.recylerview.SquareListDivider;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 10:39
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionEnrollSituationActivity extends BaseBackMvpActivity<CompetionEnrollSituationPresenterImpl>
        implements CompetionEnrollContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView rvErollList;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;


    @Inject
    public CompetionEnrollSituationPresenterImpl mPresenter;

    private int matchId = -1;

    private List<TeamBean> mTeamDatas = new ArrayList<>();
    private CompetionEnrollSituationAdapter mAdapter;

    @Override
    protected boolean initBundle(Bundle bundle) {
        if (bundle != null) {
            matchId = bundle.getInt(Constants.MATCH_ID_KEY, -1);
            LogUtils.i(matchId);
        }
        return super.initBundle(bundle);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_competion_enroll_layout;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        if (loadingLayout.isShown()) {
            if (loadingLayout.getStatus() == LoadingLayout.Loading) {
                loadingLayout.setStatus(LoadingLayout.Success);
            }
        }
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);


        rvErollList.setLayoutManager(new LinearLayoutManager(this));
        rvErollList.setHasFixedSize(true);
        rvErollList.addItemDecoration(new SquareListDivider(this,
                LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.list_divider_color)));

        mAdapter = new CompetionEnrollSituationAdapter(this, mTeamDatas);
        rvErollList.setAdapter(mAdapter);
        ((SimpleItemAnimator) rvErollList.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.isFirstOnly(false);

        loadingLayout.setEmptyText(getString(R.string.competion_enroll_emtpy));

        View view = LayoutInflater.from(this).inflate(R.layout.layout_loading, null);
        AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);

        loadingLayout.setStatus(LoadingLayout.Loading);//加载中

        if (!NetWorkUtil.isNetWorkAvailable(this)) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.doQueryCompetionEnroll(matchId);
                }
            });
            return;
        }

        swipeToLoadLayout.setLoadMoreEnabled(false);

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TeamBean bean = mAdapter.getItem(position);
                if(bean != null) {
                    TeamDetailActivity.showTeamDetail(CompetionEnrollSituationActivity.this, bean);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.doQueryCompetionEnroll(matchId);
    }

    @Override
    protected CompetionEnrollSituationPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }


    @Override
    public void updateCompetionEnrollList(EnrollSituationResult result) {

        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (result == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        } else {
            if (result.getEnrollTeamList().size() == 0) {
                if (loadingLayout != null) {
                    loadingLayout.setStatus(LoadingLayout.Empty);//加载成功
                    return;
                }
            } else {
                if (loadingLayout != null) {
                    loadingLayout.setStatus(LoadingLayout.Success);//加载成功
                }
            }
        }
        String ids = getTeamIds(result.getEnrollTeamList());
        showLoadingDialog("");
        mPresenter.doQueryTeamsBaesInfo(ids);

    }

    @Override
    public void updateTeamsBaseInfo(List<TeamBean> datas) {
        dismissLoadingDialog();
        if (datas != null) {
            mTeamDatas.clear();
            mTeamDatas.addAll(datas);
            mAdapter.notifyDataSetChanged();
        } else {
            if (loadingLayout != null) {
                loadingLayout.setStatus(LoadingLayout.Empty);//加载成功
                return;
            }
        }
    }

    @Override
    public void showError(String msg) {
        dismissLoadingDialog();
        loadingLayout.setStatus(LoadingLayout.Error);
    }


    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        if(!NetWorkUtil.isNetWorkAvailable(this)) {
            mPresenter.doQueryCompetionEnroll(matchId);
        } else {
            showToast(getString(R.string.no_network));
        }
    }

    private String getTeamIds(List<CompetionEnrollbean> datas) {
        if (datas == null || datas.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (CompetionEnrollbean bean : datas) {
            builder.append("@" + bean.getTeamId());
        }

        return builder.toString();
    }
}
