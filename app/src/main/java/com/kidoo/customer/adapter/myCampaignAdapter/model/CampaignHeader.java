package com.kidoo.customer.adapter.myCampaignAdapter.model;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 15:21
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignHeader implements ICampaignMode, Serializable {

    private String channelA;
    private String channelB;
    private String channelC;

    private String campaignPic;

    public String getChannelA() {
        return channelA;
    }

    public void setChannelA(String channelA) {
        this.channelA = channelA;
    }

    public String getChannelB() {
        return channelB;
    }

    public void setChannelB(String channelB) {
        this.channelB = channelB;
    }

    public String getChannelC() {
        return channelC;
    }

    public void setChannelC(String channelC) {
        this.channelC = channelC;
    }

    public String getCampaignPic() {
        return campaignPic;
    }

    public void setCampaignPic(String campaignPic) {
        this.campaignPic = campaignPic;
    }
}
