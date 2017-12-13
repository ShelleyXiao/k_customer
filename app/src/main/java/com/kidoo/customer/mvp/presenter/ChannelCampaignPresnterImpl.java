package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.mvp.contract.ChannelCampaignContract;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-12-13
 * Time: 14:27
 * Company: zx
 * Description:
 * FIXME
 */


public class ChannelCampaignPresnterImpl extends BasePresenterImpl<ChannelCampaignContract.View> implements ChannelCampaignContract.Presenter {

    @Inject
    public ChannelCampaignPresnterImpl() {

    }

    @Override
    public void doQueryAllChannels() {

    }
}
