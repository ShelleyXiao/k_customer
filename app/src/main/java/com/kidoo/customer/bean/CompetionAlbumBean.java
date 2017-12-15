package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 14:28
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionAlbumBean implements Serializable {

    /**
     * id : 25
     * matchId : 17
     * episodeId : null
     * shortMsg : 快乐吗????????
     * picMini : Fq54sAd8-MB6x61RUyneVe8UAw3m.jpg
     * pic : Fq54sAd8-MB6x61RUyneVe8UAw3m.jpg
     * state : null
     * committerId : 2
     * committerType : 1
     * createTime : null
     */

    private int id;
    private int matchId;
    private Object episodeId;
    private String shortMsg;
    private String picMini;
    private String pic;
    private Object state;
    private int committerId;
    private int committerType;
    private Object createTime;

    public void setId(int id) {
        this.id = id;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public void setEpisodeId(Object episodeId) {
        this.episodeId = episodeId;
    }

    public void setShortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
    }

    public void setPicMini(String picMini) {
        this.picMini = picMini;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public void setCommitterId(int committerId) {
        this.committerId = committerId;
    }

    public void setCommitterType(int committerType) {
        this.committerType = committerType;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public int getMatchId() {
        return matchId;
    }

    public Object getEpisodeId() {
        return episodeId;
    }

    public String getShortMsg() {
        return shortMsg;
    }

    public String getPicMini() {
        return picMini;
    }

    public String getPic() {
        return pic;
    }

    public Object getState() {
        return state;
    }

    public int getCommitterId() {
        return committerId;
    }

    public int getCommitterType() {
        return committerType;
    }

    public Object getCreateTime() {
        return createTime;
    }
}
