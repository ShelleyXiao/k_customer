package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-12-18
 * Time: 17:04
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamMemberBen implements Serializable {


    /**
     * id : 54
     * customerId : 51
     * teamId : 23
     * channelCId : null
     * state : null
     * createTime : null
     * updateTime : null
     */

    private int id;
    private int customerId;
    private int teamId;
    private Object channelCId;
    private Object state;
    private Object createTime;
    private Object updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Object getChannelCId() {
        return channelCId;
    }

    public void setChannelCId(Object channelCId) {
        this.channelCId = channelCId;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }
}
