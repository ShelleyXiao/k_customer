package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-16
 * Time: 20:17
 * Company: zx
 * Description:
 * FIXME
 */


public class MyCampaignResult implements Serializable{
    private int supplierId;
    private String address;
    private String content;

    private List<CampaignScore> campaignScoreList;
    private List<Episode> myEpisodeList;
    private Campaign campaign;
    private List<Episode> episodeList;

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CampaignScore> getCampaignScoreList() {
        return campaignScoreList;
    }

    public void setCampaignScoreList(List<CampaignScore> campaignScoreList) {
        this.campaignScoreList = campaignScoreList;
    }

    public List<Episode> getMyEpisodeList() {
        return myEpisodeList;
    }

    public void setMyEpisodeList(List<Episode> myEpisodeList) {
        this.myEpisodeList = myEpisodeList;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }
}
