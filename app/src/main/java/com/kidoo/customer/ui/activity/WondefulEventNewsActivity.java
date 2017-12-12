package com.kidoo.customer.ui.activity;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.WondefulEventNewsAdapter;
import com.kidoo.customer.bean.NewsBean;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.mvp.contract.WondefulEventNewsContract;
import com.kidoo.customer.mvp.presenter.WondfulEventNewsPresenterImpl;
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
 * Date: 2017-12-12
 * Time: 20:16
 * Company: zx
 * Description:
 * FIXME
 */


public class WondefulEventNewsActivity extends BaseBackMvpActivity<WondfulEventNewsPresenterImpl>
        implements WondefulEventNewsContract.View, OnRefreshListener, OnLoadMoreListener {


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

    private PageInfo mPageInfo;
    private int mPageCurrentNo = 0;
    private int mPageSizeTotal = 1;

    private List<NewsBean> mNewsBeans = new ArrayList<>();

    private WondefulEventNewsAdapter mNewsListAdapter;

    @Inject
    public WondfulEventNewsPresenterImpl mPresenter;

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
        mNewsListAdapter = new WondefulEventNewsAdapter(this, mNewsBeans);
        mRvHomeList.setAdapter(mNewsListAdapter);
        ((SimpleItemAnimator) mRvHomeList.getItemAnimator()).setSupportsChangeAnimations(false);


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

        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.doQueryList(1);
            }
        });
        if (!NetWorkUtil.isNetWorkAvailable(this)) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.doQueryList(1);
                }
            });

            return;
        }

        mPresenter.doQueryList(1);

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void updateNews(List<NewsBean> datas, PageInfo info) {

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
        mNewsListAdapter.clearAllNews();
        mNewsListAdapter.addData(datas);
        if (info == null) {
            mPageCurrentNo = 1;
            mPageSizeTotal = 1;
        } else {
            mPageSizeTotal = info.getTotalPage();
            mPageCurrentNo = info.getPageNo();
        }

        mPageCurrentNo++;
    }

    @Override
    public void loadMoreContent(List<NewsBean> datas, PageInfo info) {
        if (datas == null) {
//            mNoNet.setVisibility(View.VISIBLE);
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

        mNewsListAdapter.addData(datas);
    }

    @Override
    public void showError(String msg) {
        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (!NetWorkUtil.isNetWorkAvailable(this)) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.doQueryList(1);
                }
            });
            return;
        }

        loadingLayout.setStatus(LoadingLayout.Error);

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected WondfulEventNewsPresenterImpl initInjector() {
        mActivityComponent.inject(this);

        return mPresenter;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_wonderful_event_news;
    }

    @Override
    public void onLoadMore() {
        LogUtils.i("mPageCurrentNo = " + mPageCurrentNo + "mPageSizeTotal = " + mPageSizeTotal);
        if (mPageCurrentNo <= mPageSizeTotal) {
            mPresenter.doQueryNews(1, Constants.PAGE_SIZE, mPageCurrentNo);
            return;
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.doQueryList(1);
    }
}
