package com.kidoo.customer.ui.activity.team;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.team.QueryTeamAdapter;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.mvp.contract.team.QueryTeamContract;
import com.kidoo.customer.mvp.presenter.team.QueryTeamsListPresenterImpl;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.NetWorkUtil;
import com.kidoo.customer.widget.expandMenu.SelectMenuView;
import com.kidoo.customer.widget.recylerview.SquareListDivider;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by ShaudXiao on 2017/12/17.
 */

public class QueryTeamActivity extends BaseBackMvpActivity<QueryTeamsListPresenterImpl> implements QueryTeamContract.View
        , BaseQuickAdapter.RequestLoadMoreListener
        , SelectMenuView.OnMenuSelectDataChangedListener {


    @BindView(R.id.search_menu)
    SelectMenuView smChannelMenu;

    @BindView(R.id.met_team_name)
    MaterialEditText meTeamNameInput;

    @BindView(R.id.bt_search)
    Button btSearch;

    @BindView(R.id.swipe_target)
    RecyclerView mRvTeamList;

    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;


    private QueryTeamAdapter mTeamAdapter;

    private List<TeamBean> mTeamDatas = new ArrayList<>();

    @Inject
    public QueryTeamsListPresenterImpl mPresenter;

    private PageInfo mPageInfo;
    private int mPageCurrentNo = 1;
    private int mPagePageTotal = 1;
    private int mPageSizeTotal = 0;


    private List<ChannelA> mChannelList = new ArrayList<>();
    private int mSelectChannelID = -1;
    private int mSelectChannelAIndex = 0;
    private int mSelectChannelBIndex = 0;
    private int mSelectChannelCIndex = 0;


    @Override
    protected int getContentView() {
        return R.layout.actvity_query_team_layout;
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


        List<ChannelA> channelAList = AppContext.context().getgChannelAList();
        if (channelAList != null && channelAList.size() > 0) {
            smChannelMenu.setDataList(channelAList);
            mChannelList.addAll(channelAList);

            mSelectChannelID = channelAList.get(mSelectChannelAIndex).getChannelBList()
                    .get(mSelectChannelBIndex)
                    .getChannelCList().get(mSelectChannelCIndex)
                    .getId();
            loadingLayout.setStatus(LoadingLayout.Loading);
            mPresenter.doQueryTeamList("", mSelectChannelID, mPageCurrentNo, Constants.PAGE_SIZE);

        } else {
            if (NetWorkUtil.isNetWorkAvailable(this)) {
                mPresenter.doQueryAllChannels();
            } else {
                smChannelMenu.setVisibility(View.VISIBLE);
                loadingLayout.setStatus(LoadingLayout.No_Network);
            }

        }


        meTeamNameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int arg1, KeyEvent keyEvent) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    if (NetWorkUtil.isNetWorkAvailable(QueryTeamActivity.this)) {
                        mPageCurrentNo = 1;
                        String name = meTeamNameInput.getText().toString().trim();
                        mPresenter.doQueryTeamList(name, mSelectChannelID, mPageCurrentNo, Constants.PAGE_SIZE);
                    }
                }

                return false;
            }
        });

        addDisposable(RxView.clicks(btSearch)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String name = meTeamNameInput.getText().toString().trim();
                        if (TextUtils.isEmpty(name)) {
                            showToast(getString(R.string.query_team_name_intput_emtpy));
                        } else {
                            mPageCurrentNo = 1;
                            mPresenter.doQueryTeamList(name, mSelectChannelID, mPageCurrentNo, Constants.PAGE_SIZE);

                            loadingLayout.setStatus(LoadingLayout.Loading);
                            mTeamDatas.clear();
                            mTeamAdapter.notifyDataSetChanged();
                            mTeamAdapter.setEnableLoadMore(true);
                        }
                    }
                }));

        mTeamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        mTeamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(QueryTeamActivity.this, TeamDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.TEAM_ID_KEY, mTeamDatas.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mTeamAdapter.setEnableLoadMore(true);
        mTeamAdapter.setOnLoadMoreListener(this, mRvTeamList);

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
//
//    @OnClick({R.id.bt_search})
//    public void onSearch(final View view) {
//       Disposable disposable =  Observable.create(new ObservableOnSubscribe<View>() {
//            @Override
//            public void subscribe(ObservableEmitter<View> e) throws Exception {
//                e.onNext(view);
//            }
//        }).throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<View>() {
//                    @Override
//                    public void accept(View view) throws Exception {
//                        String name = meTeamNameInput.getText().toString().trim();
//                        if (TextUtils.isEmpty(name)) {
//                            showToast(getString(R.string.query_team_name_intput_emtpy));
//                        } else {
//                            mPageCurrentNo = 1;
//                            mPresenter.doQueryTeamList(name, mSelectChannelID, mPageCurrentNo, Constants.PAGE_SIZE);
//
//                            loadingLayout.setStatus(LoadingLayout.Loading);
//                            mTeamDatas.clear();
//                            mTeamAdapter.notifyDataSetChanged();
//                            mTeamAdapter.setEnableLoadMore(true);
//                        }
//                    }
//                });
//
//       addDisposable(disposable);
//
//    }

    @Override
    protected QueryTeamsListPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
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
        mPageCurrentNo = 1;
//        mPresenter.doQuery(mSelectChannelID);

    }

    @Override
    public void onSubjectCChanged(int indexChannalC) {
        mSelectChannelCIndex = indexChannalC;
        ChannelC selectChannelC = mChannelList.get(mSelectChannelAIndex)
                .getChannelBList().get(mSelectChannelBIndex)
                .getChannelCList().get(indexChannalC);
        mSelectChannelID = selectChannelC.getId();

        mPageCurrentNo = 1;
        String name = meTeamNameInput.getText().toString().trim();
        mPresenter.doQueryTeamList(name, mSelectChannelID, mPageCurrentNo, Constants.PAGE_SIZE);

        showLoadingDialog("");

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


        mPageCurrentNo = 1;
        String name = meTeamNameInput.getText().toString().trim();
        mPresenter.doQueryTeamList(name, mSelectChannelID, mPageCurrentNo, Constants.PAGE_SIZE);

        showLoadingDialog("");
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
                mPresenter.doQueryTeamList("", mSelectChannelID, mPageCurrentNo, Constants.PAGE_SIZE);
            }
        }

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
        String name = meTeamNameInput.getText().toString().trim();
        mPresenter.doQueryTeamList(name, mSelectChannelID, mPageCurrentNo, Constants.PAGE_SIZE);
    }
}
