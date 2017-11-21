package com.kidoo.customer.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.myCampaignAdapter.MyCampaignAdapter;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaigBaseInfo;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignHeader;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignSession;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignTeam;
import com.kidoo.customer.adapter.myCampaignAdapter.model.ICampaignMode;
import com.kidoo.customer.kidoohttp.ComParamContact;
import com.kidoo.customer.mvp.contract.MyCampaignContract;
import com.kidoo.customer.bean.Campaign;
import com.kidoo.customer.bean.CampaignScore;
import com.kidoo.customer.bean.Episode;
import com.kidoo.customer.bean.MyCampaignResult;
import com.kidoo.customer.bean.Teambase;
import com.kidoo.customer.mvp.presenter.MyCampaignPresenter;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.TDevice;
import com.kidoo.customer.utils.TimeUtils;
import com.kidoo.customer.widget.EmptyLayout;
import com.kidoo.customer.widget.RecyclerRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 11:33
 * Company: zx
 * Description:
 * FIXME
 */


public class MyCampaignFragment extends BaseFragment implements RecyclerRefreshLayout.SuperRefreshLayoutListener
        , EmptyLayout.OnClickListener
        , MyCampaignContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView mCampainInfoList;

    @BindView(R.id.fragment_content_empty)
    EmptyLayout mEmpty;

    @BindView(R.id.swiperefreshlayout)
    RecyclerRefreshLayout mRefreshLayout;

    private MyCampaignContract.Presenter mPresenter;
    private MyCampaignAdapter mCampaignAdapter;

    private List<ICampaignMode> mCampaignModes;
    private List<CampaignScore> mCampaignScores;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_campaign;
    }

    @Override
    public void initWidget(View mRoot) {
        super.initWidget(mRoot);

        mCampaignAdapter = new MyCampaignAdapter(getActivity());
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mCampainInfoList.setLayoutManager(new LinearLayoutManager(mContext));
        mCampainInfoList.setAdapter(mCampaignAdapter);
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mPresenter = new MyCampaignPresenter(getActivity(), this);
        mEmpty.setOnLayoutClickListener(this);
        mCampaignModes = new ArrayList<>();
    }

    @Override
    public void initData() {
        if (!TDevice.hasInternet()) {
            mEmpty.setVisibility(View.VISIBLE);
            mEmpty.setErrorType(EmptyLayout.NETWORK_ERROR);
            return;
        }
        mCampaignScores = new ArrayList<>();
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                if (mPresenter == null)
                    return;
                mPresenter.onRefreshCampainInfo(AccountHelper.getUserId() + "");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }


    @Override
    public void onRefreshing() {
        mCampaignScores.clear();
        mPresenter.onRefreshCampainInfo(AccountHelper.getUserId() + "");
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_content_empty) {
            if (!TDevice.hasInternet()) {
                return;
            }
            mEmpty.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mCampaignScores.clear();
                    mRefreshLayout.setRefreshing(true);
                    if (mPresenter == null)
                        return;
                    mPresenter.onRefreshCampainInfo(AccountHelper.getUserId() + "");
                }
            });

        }

    }

//    @Override
//    public void setPresenter(MyCampaignContract.Presenter presenter) {
//        this.mPresenter = presenter;
//    }

    public void showNetworkError(String str) {
        showEmptyView();
    }

    @Override
    public void updateCampainInfo(MyCampaignResult myCampaignResult) {
        if (myCampaignResult != null) {
            Campaign campaign = myCampaignResult.getCampaign();
            LogUtils.w(campaign.toString());

            if (campaign != null) {

                mCampaignModes.add(processCampaginHeader(campaign));
                mCampaignModes.addAll(processCampaignBaesInfo(campaign, myCampaignResult.getAddress(), myCampaignResult.getContent()));
            }

            List<Episode> allEpisodes = myCampaignResult.getEpisodeList();
            LogUtils.w(allEpisodes.size());
            if(allEpisodes != null && allEpisodes.size() > 0) {
                mCampaignModes.addAll(processEpisodeList(allEpisodes, true));
            }

            List<Episode> myEpisodes = myCampaignResult.getMyEpisodeList();
            LogUtils.w(myEpisodes.size());
            if(myEpisodes != null && myEpisodes.size() > 0) {
                mCampaignModes.addAll(processEpisodeList(myEpisodes, false));
            }

            mCampaignScores.addAll(myCampaignResult.getCampaignScoreList());

            mCampaignAdapter.resetItem(mCampaignModes);

        } else {
            showEmptyView();
        }

        mRefreshLayout.onComplete();
    }

    @Override
    public void updateTeambaseInfo(List<Teambase> teambases) {
        List<ICampaignMode> mTeams = new ArrayList<>();
        if(teambases != null && teambases.size() > 0) {
            for(int i = 0; i < teambases.size(); i++) {
                CampaignTeam team = new CampaignTeam();
                if(i == 0) {
                    team.setTitle(getString(R.string.campaign_scroe_title));
                }
                team.setTeamScroe(mCampaignScores.get(i));
                team.setTeambase(teambases.get(i));

                mTeams.add(team);
            }
        }
        mCampaignAdapter.addAll(mTeams);
    }

    private void showEmptyView() {
        mEmpty.setErrorType(EmptyLayout.NETWORK_ERROR);
    }

    private CampaignHeader processCampaginHeader(Campaign campaign) {
        CampaignHeader header = new CampaignHeader();

        header.setChannelA("体育");
        header.setChannelB("篮球");
        header.setChannelC("篮球(5v5)");
        header.setCampaignPic(ComParamContact.QnToken.TEST_BASE + campaign.getPic());
        return header;
    }

    private List<ICampaignMode> processCampaignBaesInfo(Campaign campaign, String addr, String promptContent) {
        List<ICampaignMode> modes = new ArrayList<>();
        CampaigBaseInfo name  = new CampaigBaseInfo();
        name.setCampaignItemName(campaign.getName());
        modes.add(name);

        CampaigBaseInfo level = new CampaigBaseInfo();
        String[] levelStrArr = getResources().getStringArray(R.array.campaign_level);
        level.setCampaignItemtitle(getString(R.string.campaign_level_title));
        level.setCamapignItemContent(levelStrArr[campaign.getLevel()]);
        modes.add(level);

        CampaigBaseInfo onGoingTime = new CampaigBaseInfo();
        String startTime = TimeUtils.millis2String(campaign.getStartTime());
        String endTime = TimeUtils.millis2String(campaign.getStopTime());
        onGoingTime.setCampaignItemtitle(getString(R.string.campaign_time_title));
        onGoingTime.setCamapignItemContent(startTime + "-" + endTime);
        modes.add(onGoingTime);

        CampaigBaseInfo formart = new CampaigBaseInfo();
        formart.setCampaignItemtitle(getString(R.string.campaign_formart_title));
        String sessionFoarmt = getString(R.string.campaign_format_session_num);
        String session = String.format(sessionFoarmt, campaign.getRoundAmount(), campaign.getEpisodeAmount());
        formart.setCamapignItemContent(getString(R.string.campaign_formart_title)
        + ":" + getResources().getStringArray(R.array.campaign_playertype)[campaign.getPlayerType()]
        + getResources().getStringArray(R.array.campaign_format)[campaign.getFormat()]
        + "," +  session);
        modes.add(formart);

        CampaigBaseInfo info = new CampaigBaseInfo();
        info.setCampaignItemtitle(getString(R.string.campaign_info_title));
        info.setCamapignItemContent(campaign.getDescription());
        modes.add(info);

        CampaigBaseInfo prompt = new CampaigBaseInfo();
//        String promptFormat = getString(R.string.campaign_format_session_ongoing);
//        String promptStr = String.format(promptFormat, promptContent);
        prompt.setCampaignItemtitle(getString(R.string.campaign_prompt_title));
        prompt.setCamapignItemContent(promptContent);
        modes.add(prompt);

        CampaigBaseInfo address = new CampaigBaseInfo();
        address.setCampaignItemtitle(getString(R.string.campaign_address_title));
        address.setCamapignItemContent(addr);
        modes.add(address);

        return modes;

    }

    private List<CampaignSession> processEpisodeList(List<Episode> episodeList, boolean all) {
        List<CampaignSession> sessionList = new ArrayList<>();
        for(int i = 0; i < episodeList.size(); i++) {
            CampaignSession session = new CampaignSession();
            if(i == 0 ) {
                if(all) {
                    session.setTitle(getString(R.string.campaign_all_round_title));
                } else {
                    session.setTitle(getString(R.string.campaign_my_round_title));
                }

            }

            session.setEpisode(episodeList.get(i));
            sessionList.add(session);
        }
        return sessionList;
    }

}
