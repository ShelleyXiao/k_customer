package com.kidoo.customer.ui.fragment;

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
import com.kidoo.customer.adapter.HomeAdapter;
import com.kidoo.customer.bean.BannerBean;
import com.kidoo.customer.bean.NewsBean;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.mvp.contract.HomeContract;
import com.kidoo.customer.mvp.presenter.HomePresenterImpl;
import com.kidoo.customer.ui.base.fragment.BaseMvpFragment;
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
 * Created by Shelley on 2017/12/10.
 */

public class HomeFragment extends BaseMvpFragment<HomePresenterImpl> implements HomeContract.View
        , OnRefreshListener, OnLoadMoreListener {


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

    @Inject
    HomePresenterImpl mPresenter;

    private PageInfo mPageInfo;
    private int mPageCurrentNo = 0;
    private int mPageSizeTotal = 1;

    private List<BannerBean> mBannerBeans = new ArrayList<>();
    private List<NewsBean> mNewsBeans = new ArrayList<>();
    private HomeAdapter mNewHomeListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        if (loadingLayout.isShown()) {
            if (loadingLayout.getStatus() == LoadingLayout.Loading) {
                loadingLayout.setStatus(LoadingLayout.Success);
            }
        }
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoadLayout.setLoadMoreFooterView(swipeLoadMoreFooter);
        mRvHomeList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvHomeList.setHasFixedSize(true);
        mRvHomeList.addItemDecoration(new SquareListDivider(mContext, LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.list_divider_color)));
        mNewHomeListAdapter = new HomeAdapter(getActivity(), mBannerBeans, mNewsBeans);
        mRvHomeList.setAdapter(mNewHomeListAdapter);
        ((SimpleItemAnimator) mRvHomeList.getItemAnimator()).setSupportsChangeAnimations(false);


        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.image);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        loadingLayout.setLoadingPage(view);
        loadingLayout.setStatus(LoadingLayout.Loading);//加载中

        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.doQueryList(1);
            }
        });
        if (!NetWorkUtil.isNetWorkAvailable(getActivity())) {
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


    }

    @Override
    protected void initData() {
        super.initData();

        mNewHomeListAdapter.setOnClickListener(new HomeAdapter.OnClickListener() {
            @Override
            public void onPostItemClick(int position, View view) {

            }

            @Override
            public void onStarRankingListClick(View view) {

            }

            @Override
            public void onBannerItemClick(int position, View view) {

            }

            @Override
            public void onBtnHeadAndNameClick(int position) {

            }
        });

        mPresenter.doQueryList(1);
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected HomePresenterImpl initInjector() {
        mFragmentComponent.inject(this);

        return mPresenter;
    }

    @Override
    public void updateBannerInfo(List<BannerBean> bannerBeans) {
        if (bannerBeans != null) {
            mNewHomeListAdapter.addBannerData(bannerBeans);
        }
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
        LogUtils.i(datas.get(0).toString());

        mNewHomeListAdapter.addData(datas);
        if(info == null) {
            mPageCurrentNo = 1;
            mPageSizeTotal = 1;
        } else {
            mPageSizeTotal = info.getTotalPage();
            mPageCurrentNo = info.getPageNo();
        }

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

        if(info == null) {
            mPageCurrentNo = 1;
            mPageSizeTotal = 1;
        } else {
            mPageSizeTotal = info.getTotalPage();
            mPageCurrentNo = info.getPageNo();
        }


        mPageCurrentNo++;

        mNewHomeListAdapter.addData(datas);
    }

    @Override
    public void showError(String msg) {
        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setRefreshing(false);
            swipeToLoadLayout.setLoadingMore(false);
        }

        if (!NetWorkUtil.isNetWorkAvailable(getActivity())) {
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

        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadMore() {
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
