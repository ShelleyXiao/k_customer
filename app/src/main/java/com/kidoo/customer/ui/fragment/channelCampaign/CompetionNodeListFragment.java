package com.kidoo.customer.ui.fragment.channelCampaign;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.adapter.competion.CompetionNodeListAdapter;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.bean.CompetionNodeBean;
import com.kidoo.customer.component.RxBus;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.widget.recylerview.SquareListDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/12/16.
 */

public class CompetionNodeListFragment extends BaseFragment {

    @BindView(R.id.rv_node)
    RecyclerView rvNodeList;

    private List<CompetionNodeBean> mNodeDatas = new ArrayList<>();
    private CompetionNodeListAdapter nodeListAdapter;

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

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        RxBus.getDefault().toObservableSticky(CompetionDetailResult.class)
                .subscribe(new Consumer<CompetionDetailResult>() {
                    @Override
                    public void accept(CompetionDetailResult result) throws Exception {
//                        LogUtils.i(result.toString());
                        if (result != null) {
                            nodeListAdapter.replaceData(result.getNodeList());
                        }
                    }
                });
    }
}
