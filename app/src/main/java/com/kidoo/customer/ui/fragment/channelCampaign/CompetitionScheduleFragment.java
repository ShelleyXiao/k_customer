package com.kidoo.customer.ui.fragment.channelCampaign;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.adapter.CompetionDetailScheduleAdapter;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.bean.CompetionEpisodeBean;
import com.kidoo.customer.component.RxBus;
import com.kidoo.customer.ui.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 10:34
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetitionScheduleFragment extends BaseFragment {

    private List<CompetionEpisodeBean> mEpisodeBeans = new ArrayList<>();
    private CompetionDetailScheduleAdapter mScheduleAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView rvEpisode;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competion_schedule_layout;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mScheduleAdapter = new CompetionDetailScheduleAdapter(getActivity(), mEpisodeBeans);
        rvEpisode.setAdapter(mScheduleAdapter);

        rvEpisode.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvEpisode.setHasFixedSize(true);

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
                            mScheduleAdapter.replaceData(result.getEpisodeList());
                        }
                    }
                });
    }
}
