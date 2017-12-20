package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 10:17
 * Company: zx
 * Description:
 * FIXME
 */


public class EnrollCustomerBean implements Serializable {


    /**
     * id : 59
     * matchId : 17
     * teamId : 6
     * customerId : 2
     * state : 1
     * createTime : null
     */

    private int id;
    private int matchId;
    private int teamId;
    private int customerId;
    private int state;
    private Object createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "EnrollCustomerBean{" +
                "id=" + id +
                ", matchId=" + matchId +
                ", teamId=" + teamId +
                ", customerId=" + customerId +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
