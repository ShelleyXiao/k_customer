package com.kidoo.customer.ui.fragment.channelCampaign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.competion.CompetionNodeListAdapter;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.bean.CompetionNodeBean;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.component.RxBus;
import com.kidoo.customer.ui.activity.user.UserAddMatchNodeActivity;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.widget.EmptyLayout;
import com.kidoo.customer.widget.recylerview.SquareListDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/12/16.
 */

public class CompetionNodeListFragment extends BaseFragment {

    @BindView(R.id.rv_node)
    RecyclerView rvNodeList;

    @BindView(R.id.bt_add_node)
    Button btAddNode;

    @BindView(R.id.error_layout)
    EmptyLayout elEmptylayout;

    private List<CompetionNodeBean> mNodeDatas = new ArrayList<>();
    private CompetionNodeListAdapter nodeListAdapter;

    private MatchBean mMatchBean;
    private boolean fromManager = false;

    private CompetionDetailResult mDetailResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mMatchBean = (MatchBean) bundle.getSerializable(Constants.MATCH_BEAN_DATA_KEY);
        fromManager = bundle.getBoolean(Constants.FROM_MAMAGER_KEY, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competion_node_list_layout;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        nodeListAdapter = new CompetionNodeListAdapter(getActivity(), mNodeDatas);
        rvNodeList.setAdapter(nodeListAdapter);

        rvNodeList.setLayoutManager(new LinearLayoutManager(mContext));
        rvNodeList.setHasFixedSize(true);
        rvNodeList.addItemDecoration(new SquareListDivider(mContext, LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.list_divider_color)));

        if (fromManager) {
            btAddNode.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        RxBus.getDefault().toObservableSticky(CompetionDetailResult.class)
                .subscribe(new Consumer<CompetionDetailResult>() {
                    @Override
                    public void accept(CompetionDetailResult result) throws Exception {
//                        LogUtils.i(result.toString());

                        if (result != null && (result.getNodeList() != null && result.getNodeList().size() > 0)) {

                            nodeListAdapter.replaceData(result.getNodeList());
                            mDetailResult = result;

                        } else {
                            if (!fromManager && elEmptylayout != null) {
                                elEmptylayout.setVisibility(View.VISIBLE);
                                elEmptylayout.setErrorType(EmptyLayout.NODATA);
                            }
                        }
                    }
                });

        RxBus.getDefault().toObservableSticky(CompetionNodeBean.class).
                subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CompetionNodeBean>() {
                    @Override
                    public void accept(CompetionNodeBean competionNodeBean) throws Exception {
                        if (competionNodeBean != null) {
                            nodeListAdapter.addData(competionNodeBean);
                            List<CompetionNodeBean> nodeBeanList = mDetailResult.getNodeList();
                            if (null != nodeBeanList) {
                                nodeBeanList.add(competionNodeBean);
                            } else {
                                nodeBeanList = new ArrayList<>();
                                nodeBeanList.add(competionNodeBean);
                                mDetailResult.setNodeList(nodeBeanList);
                                if (!fromManager && elEmptylayout != null) {
                                    elEmptylayout.setVisibility(View.GONE);
                                }
                                nodeListAdapter.replaceData(nodeBeanList);
                            }
                        }
                    }
                });
    }

    @OnClick(R.id.bt_add_node)
    public void addNode() {
        Intent intent = new Intent(getActivity(), UserAddMatchNodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MATCH_ID_KEY, mMatchBean.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
