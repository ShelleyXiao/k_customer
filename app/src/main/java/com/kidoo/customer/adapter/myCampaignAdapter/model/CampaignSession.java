package com.kidoo.customer.adapter.myCampaignAdapter.model;

import com.kidoo.customer.mvp.model.Episode;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 15:30
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignSession implements Serializable, ICampaignMode{
    private String title;

    private Episode mEpisode;

    public Episode getEpisode() {
        return mEpisode;
    }

    public void setEpisode(Episode episode) {
        mEpisode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
