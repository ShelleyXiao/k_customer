package com.kidoo.customer.mvp.model;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-16
 * Time: 20:12
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignScore implements Serializable {

    /**
     * id : 5
     * campaignId : 28
     * playerId : null
     * teamId : 5
     * score : 1
     * createTime : 1506067996000
     */

    private int id;
    private int campaignId;
    private Object playerId;
    private int teamId;
    private int score;
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public Object getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Object playerId) {
        this.playerId = playerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
