package com.kidoo.customer.ui.activity.channelCampaign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.component.RxBus;
import com.kidoo.customer.di.Component.ActivityComponent;
import com.kidoo.customer.di.Component.DaggerActivityComponent;
import com.kidoo.customer.di.module.ActivityModule;
import com.kidoo.customer.mvp.contract.channelCampaign.CompetionDetailContract;
import com.kidoo.customer.mvp.presenter.channelCampaign.CompetionDetailPresenterImpl;
import com.kidoo.customer.ui.base.activities.BaseViewPagerActivity;
import com.kidoo.customer.ui.fragment.channelCampaign.CampaignBaseInfoFragment;
import com.kidoo.customer.ui.fragment.channelCampaign.CompetionAlbumFragment;
import com.kidoo.customer.ui.fragment.channelCampaign.CompetionMedalListFragment;
import com.kidoo.customer.ui.fragment.channelCampaign.CompetionNodeListFragment;
import com.kidoo.customer.ui.fragment.channelCampaign.CompetitionScheduleFragment;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.tablayout.CustomTabEntity;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 19:26
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignDetailActivity extends BaseViewPagerActivity implements CompetionDetailContract.View{

    protected ActivityComponent mActivityComponent;

    @Inject
    protected CompetionDetailPresenterImpl mPresenter;

    private MatchBean mMatchBean;

    private Bundle mBundle;


    public static void showMatchDetail(Context context, MatchBean matchBean, boolean fromManager) {
        Intent intent = new Intent(context, CampaignDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MATCH_BEAN_DATA_KEY,matchBean);
        bundle.putBoolean(Constants.FROM_MAMAGER_KEY, fromManager);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public boolean initBundle(Bundle bundle) {
        if(bundle != null) {
            mMatchBean = (MatchBean) bundle.getSerializable(Constants.MATCH_BEAN_DATA_KEY);
        }

        mBundle = bundle;
        LogUtils.d(mMatchBean);
        return super.initBundle(bundle);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((AppContext) getApplication()).getAppComponent())
                .build();

        mActivityComponent.inject(this);

        mPresenter.attachView(this);



    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        if(mMatchBean != null) {
            int matchId = mMatchBean.getId();
            mPresenter.doQueryCompetionDetail(matchId);
        }
    }

    @Override
    protected PagerInfo[] getPagers() {
        return new PagerInfo[]{
                new PagerInfo(getString(R.string.campaign_detail_title), CampaignBaseInfoFragment.class, mBundle),
                new PagerInfo(getString(R.string.campaign_detail_schedule), CompetitionScheduleFragment.class, mBundle),
                new PagerInfo(getString(R.string.campaign_detail_award), CompetionMedalListFragment.class, mBundle),
                new PagerInfo(getString(R.string.campaign_detail_ablum), CompetionAlbumFragment.class, mBundle),
                new PagerInfo(getString(R.string.campaign_detail_node), CompetionNodeListFragment.class, mBundle),

        };
    }

    @Override
    protected ArrayList<CustomTabEntity> getTabEntity() {
        ArrayList<CustomTabEntity> customTabEntities = new ArrayList<>();
        customTabEntities.add(new TabEntity(getString(R.string.campaign_detail_title), R.drawable.tab_icon_me, R.drawable.tab_icon_me));
        customTabEntities.add(new TabEntity(getString(R.string.campaign_detail_schedule), R.drawable.tab_icon_me, R.drawable.tab_icon_me));
        customTabEntities.add(new TabEntity(getString(R.string.campaign_detail_award), R.drawable.tab_icon_me, R.drawable.tab_icon_me));
        customTabEntities.add(new TabEntity(getString(R.string.campaign_detail_ablum), R.drawable.tab_icon_me, R.drawable.tab_icon_me));
        customTabEntities.add(new TabEntity(getString(R.string.campaign_detail_node), R.drawable.tab_icon_me, R.drawable.tab_icon_me));
        return customTabEntities;
    }

    @Override
    public void updateCompetionDetail(CompetionDetailResult result) {
        LogUtils.i(result.getMatch().toString());
        if(result != null) {
            RxBus.getDefault().postSticky(result);
        }
    }

    @Override
    public void showError(String msg) {

    }
}
