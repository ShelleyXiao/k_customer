package com.kidoo.customer.ui.activity.channelCampaign;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.mvp.contract.ChannelCampaignContract;
import com.kidoo.customer.mvp.presenter.ChannelCampaignPresnterImpl;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;

/**
 * User: ShaudXiao
 * Date: 2017-12-13
 * Time: 14:29
 * Company: zx
 * Description:
 * FIXME
 */


public class ChannelCampaignListActivtiy extends BaseBackMvpActivity<ChannelCampaignPresnterImpl>
        implements ChannelCampaignContract.View {


    @Override
    public void showToast(String msg) {

    }

    @Override
    public void updateUserInfo(AllChannelResultBean channelResultBean) {

    }

    @Override
    protected ChannelCampaignPresnterImpl initInjector() {
        return null;
    }

    @Override
    protected int getContentView() {
        return 0;
    }
}
