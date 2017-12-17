package com.kidoo.customer.ui.activity.channelCampaign;

import android.content.Intent;
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
import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.competion.ChannelCampaignAdapter;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.mvp.contract.channelCampaign.ChannelCampaignContract;
import com.kidoo.customer.mvp.presenter.channelCampaign.ChannelCampaignPresnterImpl;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.NetWorkUtil;
import com.kidoo.customer.widget.expandMenu.SelectMenuView;
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
 * Date: 2017-12-13
 * Time: 14:29
 * Company: zx
 * Description:
 * FIXME
 */


public class ChannelCampaignListActivtiy extends BaseBackMvpActivity<ChannelCampaignPresnterImpl>
        implements ChannelCampaignContract.View, OnRefreshListener, OnLoadMoreListener
        , SelectMenuView.OnMenuSelectDataChangedListener {

    @BindView(R.id.search_menu)
    SelectMenuView smChannelMenu;

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

    private List<MatchBean> mMatchBeans = new ArrayList<>();

    private ChannelCampaignAdapter mAdapter;

    @Inject
    ChannelCampaignPresnterImpl mPresenter;


    private List<ChannelA> mChannelList = new ArrayList<>();
    private int mSelectChannelID = -1;
    private int mSelectChannelAIndex = 0;
    private int mSelectChannelBIndex = 0;
    private int mSelectChannelCIndex = 0;

    @Override
    protected void initWidget() {
        super.initWidget();

        smChannelMenu.setOnMenuSelectDataChangedListener(this);

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

        mAdapter = new ChannelCampaignAdapter(this, mMatchBeans);
        mRvHomeList.setAdapter(mAdapter);
        ((SimpleItemAnimator) mRvHomeList.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.isFirstOnly(false);

        loadingLayout.setEmptyText(getString(R.string.channel_hava_no_campaign_data));

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
                    mPresenter.doQuery(mSelectChannelID);
                }
            });
            return;
        }

    }


    @Override
    protected void initEventAndData() {
        super.initEventAndData();


        List<ChannelA> channelAList = AppContext.context().getgChannelAList();
        if (channelAList != null && channelAList.size() > 0) {
            smChannelMenu.setDataList(channelAList);
            mChannelList.addAll(channelAList);

            mSelectChannelID = channelAList.get(mSelectChannelAIndex).getChannelBList()
                    .get(mSelectChannelBIndex)
                    .getChannelCList().get(mSelectChannelCIndex)
                    .getId();
            loadingLayout.setStatus(LoadingLayout.Loading);
            mPresenter.doQuery(mSelectChannelID);

        } else {
            mPresenter.doQueryAllChannels();

        }

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.d("onItemClick: ");
                MatchBean bean = mMatchBeans.get(position);
                if (null != bean) {
                    Intent intent = new Intent(ChannelCampaignListActivtiy.this, CampaignDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.SELECT_A_INDEX, mSelectChannelAIndex);
                    bundle.putInt(Constants.SELECT_B_INDEX, mSelectChannelBIndex);
                    bundle.putInt(Constants.SELECT_C_INDEX, mSelectChannelCIndex);
                    bundle.putSerializable(Constants.MATCH_BEAN_DATA_KEY, bean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showToast(String msg) {

    }


    @Override
    protected ChannelCampaignPresnterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_channel_campaign_list;
    }

    @Override
    public void updateChannelInfo(AllChannelResultBean channelResultBean) {
        if (mChannelList == null) {
            mChannelList = new ArrayList<>();
        }
        if (channelResultBean != null) {
            LogUtils.i(channelResultBean.getChannelAList().get(0).getDescription()
                    + channelResultBean.getChannelAList().size()
            );
            if (channelResultBean.getChannelAList() != null) {
                mChannelList.clear();
                List<ChannelA> dataList = new ArrayList<>();
                dataList.addAll(channelResultBean.getChannelAList());
                smChannelMenu.setDataList(dataList);
                mChannelList.addAll(dataList);

                AppContext.context().setgChannelAList(dataList);

                mSelectChannelID = dataList.get(mSelectChannelAIndex).getChannelBList()
                        .get(mSelectChannelBIndex)
                        .getChannelCList().get(mSelectChannelCIndex)
                        .getId();

                loadingLayout.setStatus(LoadingLayout.Loading);
                mPresenter.doQuery(mSelectChannelID);

            }
        }
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
        LogUtils.i(datas.get(0).toString());
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
    }

    @Override
    public void loadMoreContent(List<MatchBean> datas, PageInfo info) {
        LogUtils.i(" ");

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

    }

    @Override
    public void showError(String msg, ChannelCampaignContract.Type type) {

        if (!NetWorkUtil.isNetWorkAvailable(this)) {
            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            //为ReloadButton设置监听
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.doQuery(mSelectChannelID);
                }
            });
            return;
        }

        if (type == ChannelCampaignContract.Type.all) {
            if (mAdapter.getData().size() == 0) {
                loadingLayout.setStatus(LoadingLayout.Error);
            } else {

            }
        } else if (type == ChannelCampaignContract.Type.more) {

        }
    }

    @Override
    public void onLoadMore() {
        LogUtils.i("mPageCurrentNo = " + mPageCurrentNo + "mPageSizeTotal = " + mPageSizeTotal);
        if (mPageCurrentNo <= mPageSizeTotal) {
            mPresenter.doQueryMatchs(mSelectChannelID, Constants.PAGE_SIZE, mPageCurrentNo);
            return;
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.doQuery(mSelectChannelID);
    }


    @Override
    public void onSubjectABChanged(int indexChannalA, int indexChannalB) {

        mSelectChannelAIndex = indexChannalA;
        mSelectChannelBIndex = indexChannalB;
        mSelectChannelCIndex = 0;

        ChannelC selectChannelC = mChannelList.get(indexChannalA)
                .getChannelBList().get(indexChannalB)
                .getChannelCList().get(mSelectChannelCIndex);


        mSelectChannelID = selectChannelC.getId();

        mPresenter.doQuery(mSelectChannelID);

    }

    @Override
    public void onSubjectCChanged(int indexChannalC) {
        mSelectChannelCIndex = indexChannalC;
        ChannelC selectChannelC = mChannelList.get(mSelectChannelAIndex)
                .getChannelBList().get(mSelectChannelBIndex)
                .getChannelCList().get(indexChannalC);
        mSelectChannelID = selectChannelC.getId();


        mPresenter.doQuery(mSelectChannelID);
    }

    @Override
    public void onViewClicked(View view) {

    }

    @Override
    public void onSelectedDismissed(int indexChannalA, int indexChannalB) {
        ChannelC selectChannelC = mChannelList.get(indexChannalA)
                .getChannelBList().get(indexChannalB)
                .getChannelCList().get(mSelectChannelCIndex);
        mSelectChannelID = selectChannelC.getId();


        mPresenter.doQuery(mSelectChannelID);
    }
}
