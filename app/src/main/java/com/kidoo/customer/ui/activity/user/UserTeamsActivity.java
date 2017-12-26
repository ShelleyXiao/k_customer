package com.kidoo.customer.ui.activity.user;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.team.QueryTeamAdapter;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.mvp.contract.user.UserTeamsContract;
import com.kidoo.customer.mvp.presenter.user.UsersTeamsListPresenterImpl;
import com.kidoo.customer.ui.activity.team.TeamDetailActivity;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.recylerview.SquareListDivider;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-12-26
 * Time: 11:32
 * Company: zx
 * Description:
 * FIXME
 */


public class UserTeamsActivity extends BaseBackMvpActivity<UsersTeamsListPresenterImpl> implements UserTeamsContract.View
        , BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.swipe_target)
    RecyclerView mRvTeamList;

    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;

    @BindView(R.id.bt_create_team)
    Button btCreateTeam;


    private QueryTeamAdapter mTeamAdapter;

    private List<TeamBean> mTeamDatas = new ArrayList<>();

    @Inject
    public UsersTeamsListPresenterImpl mPresenter;

    private PageInfo mPageInfo;
    private int mPageCurrentNo = 1;
    private int mPagePageTotal = 1;
    private int mPageSizeTotal = 0;


    @Override
    protected int getContentView() {
        return R.layout.actvity_user_teams_layout;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        if (loadingLayout.isShown()) {
            if (loadingLayout.getStatus() == LoadingLayout.Loading) {
                loadingLayout.setStatus(LoadingLayout.Success);
            }
        }

        mRvTeamList.setLayoutManager(new LinearLayoutManager(this));
        mRvTeamList.setHasFixedSize(true);
        mRvTeamList.addItemDecoration(new SquareListDivider(this,
                LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.list_divider_color)));

        mTeamAdapter = new QueryTeamAdapter(this, mTeamDatas);
        mRvTeamList.setAdapter(mTeamAdapter);
        ((SimpleItemAnimator) mRvTeamList.getItemAnimator()).setSupportsChangeAnimations(false);

        loadingLayout.setEmptyText(getString(R.string.query_team_empty));
        View view = LayoutInflater.from(this).inflate(R.layout.layout_loading, null);
        AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();


        mTeamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                TeamDetailActivity.showTeamDetail(UserTeamsActivity.this,
                        mTeamDatas.get(position));
            }
        });

        mTeamAdapter.setEnableLoadMore(true);
        mTeamAdapter.setOnLoadMoreListener(this, mRvTeamList);

        mPresenter.doQueryTeamList(AccountHelper.getUserId() + "", mPageCurrentNo, Constants.PAGE_SIZE);

    }

    @Override
    public void updateTeamList(List<TeamBean> teamBeans, PageInfo pageInfo) {
        dismissLoadingDialog();
        if (teamBeans == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        } else {
            if (loadingLayout != null) {
                loadingLayout.setStatus(LoadingLayout.Success);//加载成功
            }
        }
        LogUtils.i(teamBeans.get(0).toString());
        mTeamDatas.addAll(teamBeans);
        mTeamAdapter.notifyDataSetChanged();
        if (pageInfo == null) {
            mPageCurrentNo = 1;
            mPagePageTotal = 1;
        } else {
            mPagePageTotal = pageInfo.getTotalPage();
            mPageCurrentNo = pageInfo.getPageNo();
            LogUtils.i(pageInfo.toString());
            mPageSizeTotal = pageInfo.getTotalRecord();

            LogUtils.i(pageInfo.toString());
        }

        mPageCurrentNo++;

        if (mTeamAdapter.getData().size() >= mPageSizeTotal) {
            mTeamAdapter.loadMoreComplete();
            mTeamAdapter.loadMoreEnd(true);
        }

    }

    @Override
    public void showError(String msg) {
        showToast(msg);
        dismissLoadingDialog();

        mTeamAdapter.loadMoreFail();
        loadingLayout.setStatus(LoadingLayout.Empty);
    }


    @Override
    protected UsersTeamsListPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }


    @Override
    public void onLoadMoreRequested() {
        LogUtils.i("onLoadMoreRequested"
                + "size = " + mTeamAdapter.getData().size()
                + " mPagePageTotal = " + mPageSizeTotal);

        if (mTeamAdapter.getData().size() >= mPageSizeTotal) {
            mTeamAdapter.loadMoreComplete();
            mTeamAdapter.loadMoreEnd(true);
            return;
        }

        mPresenter.doQueryTeamList(AccountHelper.getUserId() + "", mPageCurrentNo, Constants.PAGE_SIZE);
    }
}

