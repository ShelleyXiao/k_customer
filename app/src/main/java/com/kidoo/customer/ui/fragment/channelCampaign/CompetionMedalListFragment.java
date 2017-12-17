package com.kidoo.customer.ui.fragment.channelCampaign;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.adapter.competion.CompetionMedalListAdapter;
import com.kidoo.customer.adapter.itemDecoration.SpaceItemDecoration;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.bean.MedalBean;
import com.kidoo.customer.component.RxBus;
import com.kidoo.customer.ui.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/12/16.
 */

public class CompetionMedalListFragment extends BaseFragment {

    @BindView(R.id.medal_list)
    RecyclerView rvMedalList;

    private List<MedalBean> mMedalDatas = new ArrayList<>();
    private CompetionMedalListAdapter medalListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competion_medal_list_layout;
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        medalListAdapter = new CompetionMedalListAdapter(getActivity(), mMedalDatas);
        rvMedalList.setAdapter(medalListAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        rvMedalList.setLayoutManager(gridLayoutManager);
        rvMedalList.setHasFixedSize(true);
        rvMedalList.addItemDecoration(new SpaceItemDecoration(8));

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
                            medalListAdapter.replaceData(result.getMedalList());
                        }
                    }
                });
//        medalListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                MedalBean bean = (MedalBean) adapter.getData().get(position);
//                if (bean != null) {
//
//                    String picUrl = AppContext.context().getInitData().getQnDomain() + bean.getPic();
//                    if (!TextUtils.isEmpty(picUrl)) {
//                        ImageGalleryActivity.show(getActivity(), picUrl);
//                    }
//                }
//            }
//        });
    }
}
