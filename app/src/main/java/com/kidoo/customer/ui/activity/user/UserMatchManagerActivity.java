package com.kidoo.customer.ui.activity.user;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.UserMatchsManagerAdapter;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.mvp.contract.user.UserMatchsManagerContract;
import com.kidoo.customer.mvp.presenter.user.UserMatchsManagerPresenterImpl;
import com.kidoo.customer.ui.activity.channelCampaign.CampaignDetailActivity;
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
import butterknife.OnClick;

/**
 * User: ShaudXiao
 * Date: 2017-12-22
 * Time: 10:05
 * Company: zx
 * Description:
 * FIXME
 */


public class UserMatchManagerActivity extends BaseBackMvpActivity<UserMatchsManagerPresenterImpl>
        implements UserMatchsManagerContract.View, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView mRvHomeList;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.bt_create_competion)
    Button btCreateMatch;


    private PageInfo mPageInfo;
    private int mPageCurrentNo = 0;
    private int mPageSizeTotal = 1;

    private List<MatchBean> mMatchBeans = new ArrayList<>();
    private UserMatchsManagerAdapter mAdapter;

    @Inject
    public UserMatchsManagerPresenterImpl mPresenter;

    @Override
    protected UserMatchsManagerPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_match_manager;
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


        mRvHomeList.setLayoutManager(new LinearLayoutManager(this));
        mRvHomeList.setHasFixedSize(true);
        mRvHomeList.addItemDecoration(new SquareListDivider(this,
                LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.list_divider_color)));

        mAdapter = new UserMatchsManagerAdapter(this, mMatchBeans);
        mRvHomeList.setAdapter(mAdapter);
        ((SimpleItemAnimator) mRvHomeList.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.isFirstOnly(false);

        loadingLayout.setEmptyText(getString(R.string.competion_manager_empty));

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
                    mPresenter.doQuery(AccountHelper.getUserId());
                }
            });
            return;
        }

    }


    @Override
    protected void initEventAndData() {
        super.initEventAndData();


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.d("onItemClick: ");
                MatchBean bean = mMatchBeans.get(position);
                if (null != bean) {
//                    Intent intent = new Intent(UserMatchManagerActivity.this, CampaignDetailActivity.class);
//                    Bundle bundle = new Bundle();
////                    bundle.putInt(Constants.SELECT_A_INDEX, mSelectChannelAIndex);
////                    bundle.putInt(Constants.SELECT_B_INDEX, mSelectChannelBIndex);
////                    bundle.putInt(Constants.SELECT_C_INDEX, mSelectChannelCIndex);
//                    bundle.putSerializable(Constants.MATCH_BEAN_DATA_KEY, bean);
//                    intent.putExtras(bundle);
//                    startActivity(intent);

                    CampaignDetailActivity.showMatchDetail(UserMatchManagerActivity.this, bean, true);
                }
            }
        });

        mPresenter.doQuery(AccountHelper.getUserId());

    }

    @Override
    public void onLoadMore() {
        LogUtils.i("mPageCurrentNo = " + mPageCurrentNo + "mPageSizeTotal = " + mPageSizeTotal);
        if (mPageCurrentNo <= mPageSizeTotal) {
            mPresenter.doQueryMatchsManager(AccountHelper.getUserId(), Constants.PAGE_SIZE, mPageCurrentNo);
            return;
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.doQuery(AccountHelper.getUserId());
    }

    @Override
    public void updateMatch(List<MatchBean> datas, PageInfo info) {
        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (datas == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        } else {
            if (loadingLayout != null) {
                loadingLayout.setStatus(LoadingLayout.Success);//加载成功
            }
        }
//        LogUtils.i(datas.get(0).toString());
        mMatchBeans.clear();
        mMatchBeans.addAll(datas);
        mAdapter.notifyDataSetChanged();
        if (info == null) {
            mPageCurrentNo = 1;
            mPageSizeTotal = 1;
        } else {
            mPageSizeTotal = info.getTotalPage();
            mPageCurrentNo = info.getPageNo();
        }

        mPageCurrentNo++;
        if(mPageCurrentNo >= mPageSizeTotal) {
            swipeToLoadLayout.setLoadMoreEnabled(false);
        }
    }

    @Override
    public void loadMoreContent(List<MatchBean> datas, PageInfo info) {
        if (datas == null) {
            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
            return;
        } else {
            if (loadingLayout != null) {
                loadingLayout.setStatus(LoadingLayout.Success);//加载成功
            }
        }

        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (info == null) {
            mPageCurrentNo = 1;
            mPageSizeTotal = 1;
        } else {
            mPageSizeTotal = info.getTotalPage();
            mPageCurrentNo = info.getPageNo();
        }

        LogUtils.i("mPageCurrentNo = " + mPageCurrentNo + "mPageSizeTotal = " + mPageSizeTotal);

        mPageCurrentNo++;

        mAdapter.addData(datas);
        if(mPageCurrentNo >= mPageSizeTotal) {
            swipeToLoadLayout.setLoadMoreEnabled(false);
        }
    }

    @Override
    public void showError(String msg, UserMatchsManagerContract.Type type) {
        if (!NetWorkUtil.isNetWorkAvailable(this)) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.doQuery(AccountHelper.getUserId());
                }
            });

            return;
        }

        if (type == UserMatchsManagerContract.Type.all) {
            if (mAdapter.getData().size() == 0) {
                loadingLayout.setStatus(LoadingLayout.Error);
            } else {

            }
        } else if (type == UserMatchsManagerContract.Type.more) {

        }
    }

    @OnClick({R.id.bt_create_competion})
    public void createCompetion() {
        Intent intent = new Intent(this, UserCreateCompetionActivity.class);
        startActivity(intent);
    }

}