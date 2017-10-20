package com.kidoo.customer.adapter.myCampaignAdapter.model;

import com.kidoo.customer.mvp.model.CampaignScore;
import com.kidoo.customer.mvp.model.Teambase;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 15:30
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignTeam implements Serializable, ICampaignMode{
    private String title;

    private Teambase teambase;

    private CampaignScore teamScroe;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Teambase getTeambase() {
        return teambase;
    }

    public void setTeambase(Teambase teambase) {
        this.teambase = teambase;
    }

    public CampaignScore getTeamScroe() {
        return teamScroe;
    }

    public void setTeamScroe(CampaignScore teamScroe) {
        this.teamScroe = teamScroe;
    }
}
