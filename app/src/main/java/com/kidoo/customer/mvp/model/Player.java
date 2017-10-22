package com.kidoo.customer.mvp.model;

import java.io.Serializable;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/10/22  
 * update: 2017/10/22
 * version: 
*/

public class Player implements Serializable {


    /**
     * id : 1972
     * broadcastId : 110
     * teamId : 5
     * customerId : 1
     * status : 1
     * createTime : 1506069180000
     * avgScore : 5.9733
     */

    private int id;
    private int broadcastId;
    private int teamId;
    private int customerId;
    private int status;
    private long createTime;
    private double avgScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(int broadcastId) {
        this.broadcastId = broadcastId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }
}
