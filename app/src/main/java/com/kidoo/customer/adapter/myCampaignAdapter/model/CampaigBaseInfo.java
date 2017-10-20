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


public class CampaigBaseInfo implements ICampaignMode, Serializable {

    private String campaignItemName;
    private String campaignItemtitle;
    private String camapignItemContent;

    public String getCampaignItemtitle() {
        return campaignItemtitle;
    }

    public void setCampaignItemtitle(String campaignItemtitle) {
        this.campaignItemtitle = campaignItemtitle;
    }

    public String getCampaignItemName() {
        return campaignItemName;
    }

    public void setCampaignItemName(String campaignItemName) {
        this.campaignItemName = campaignItemName;
    }

    public String getCamapignItemContent() {
        return camapignItemContent;
    }

    public void setCamapignItemContent(String camapignItemContent) {
        this.camapignItemContent = camapignItemContent;
    }
}
