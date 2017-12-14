package com.kidoo.customer.ui.activity.channelCampaign;

import android.os.Bundle;

import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.ui.base.activities.BaseViewPagerActivity;
import com.kidoo.customer.ui.fragment.channelCampaign.CampaignBaseInfoFragment;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.tablayout.CustomTabEntity;

import java.util.ArrayList;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 19:26
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignDetailActivity extends BaseViewPagerActivity {


    private MatchBean mMatchBean;

    private Bundle mBundle;

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
    protected PagerInfo[] getPagers() {
        return new PagerInfo[]{
                new PagerInfo(getString(R.string.campaign_detail_title), CampaignBaseInfoFragment.class, mBundle),
                new PagerInfo(getString(R.string.campaign_detail_schedule), CampaignBaseInfoFragment.class, mBundle),
                new PagerInfo(getString(R.string.campaign_detail_award), CampaignBaseInfoFragment.class, mBundle),
                new PagerInfo(getString(R.string.campaign_detail_ablum), CampaignBaseInfoFragment.class, mBundle),
                new PagerInfo(getString(R.string.campaign_detail_node), CampaignBaseInfoFragment.class, mBundle),

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
}
