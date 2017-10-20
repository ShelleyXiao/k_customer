package com.kidoo.customer.mvp.model;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-16
 * Time: 20:14
 * Company: zx
 * Description: 经历的赛事
 * FIXME
 */


public class Episode implements Serializable{

    /**
     * id : 89
     * no : 1
     * campaignId : 28
     * roundId : 46
     * roundNo : 1
     * enrollSupplierId : 30
     * enrollSupplierNo : 1
     * supplierReward : 1
     * playerAmount : 20
     * victorAmount : 10
     * name : 1
     * description : 测试篮球积分赛
     * pic : kidoo/2017-09-22/15-42/mv (2).jpg
     * status : 2
     * startTime : 1506063600000
     * stopTime : 1506071100000
     * playerList : null
     */

    private int id;
    private int no;
    private int campaignId;
    private int roundId;
    private int roundNo;
    private int enrollSupplierId;
    private int enrollSupplierNo;
    private int supplierReward;
    private int playerAmount;
    private int victorAmount;
    private String name;
    private String description;
    private String pic;
    private int status;
    private long startTime;
    private long stopTime;
    private Object playerList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getRoundNo() {
        return roundNo;
    }

    public void setRoundNo(int roundNo) {
        this.roundNo = roundNo;
    }

    public int getEnrollSupplierId() {
        return enrollSupplierId;
    }

    public void setEnrollSupplierId(int enrollSupplierId) {
        this.enrollSupplierId = enrollSupplierId;
    }

    public int getEnrollSupplierNo() {
        return enrollSupplierNo;
    }

    public void setEnrollSupplierNo(int enrollSupplierNo) {
        this.enrollSupplierNo = enrollSupplierNo;
    }

    public int getSupplierReward() {
        return supplierReward;
    }

    public void setSupplierReward(int supplierReward) {
        this.supplierReward = supplierReward;
    }

    public int getPlayerAmount() {
        return playerAmount;
    }

    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
    }

    public int getVictorAmount() {
        return victorAmount;
    }

    public void setVictorAmount(int victorAmount) {
        this.victorAmount = victorAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public Object getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Object playerList) {
        this.playerList = playerList;
    }
}
