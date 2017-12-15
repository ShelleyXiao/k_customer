package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * Created by Shelley on 2017/12/2.
 */

public class MedalBean implements Serializable {

    /**
     * id : 44
     * channelCId : 4
     * matchId : 17
     * customerId : null
     * teamId : null
     * level : 1
     * name : 个人奖
     * picMini : Fh2R6W2mNL8cg_v15NP_ntZJqTrj.png
     * pic : Fh2R6W2mNL8cg_v15NP_ntZJqTrj.png
     * msgMini : null
     * msg : null
     * playerType : 0
     * state : 0
     * createTime : null
     * updateTime : null
     */

    private int id;
    private int channelCId;
    private int matchId;
    private Object customerId;
    private Object teamId;
    private int level;
    private String name;
    private String picMini;
    private String pic;
    private Object msgMini;
    private Object msg;
    private int playerType;
    private int state;
    private Object createTime;
    private Object updateTime;

    public void setId(int id) {
        this.id = id;
    }

    public void setChannelCId(int channelCId) {
        this.channelCId = channelCId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public void setCustomerId(Object customerId) {
        this.customerId = customerId;
    }

    public void setTeamId(Object teamId) {
        this.teamId = teamId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicMini(String picMini) {
        this.picMini = picMini;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setMsgMini(Object msgMini) {
        this.msgMini = msgMini;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public int getChannelCId() {
        return channelCId;
    }

    public int getMatchId() {
        return matchId;
    }

    public Object getCustomerId() {
        return customerId;
    }

    public Object getTeamId() {
        return teamId;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getPicMini() {
        return picMini;
    }

    public String getPic() {
        return pic;
    }

    public Object getMsgMini() {
        return msgMini;
    }

    public Object getMsg() {
        return msg;
    }

    public int getPlayerType() {
        return playerType;
    }

    public int getState() {
        return state;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }
}
