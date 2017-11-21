package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-16
 * Time: 20:15
 * Company: zx
 * Description:
 * FIXME
 */


public class Campaign implements Serializable {


    /**
     * id : 28
     * awardId : 21
     * channelAId : 3
     * channelBId : 4
     * channelCId : 4
     * name : 测试篮球积分赛
     * charge : 1
     * playerAmount : 40
     * teamAmount : 4
     * supplierAmount : 1
     * firstRoundArea : 0
     * level : 0
     * playerType : 1
     * enrollType : 0
     * format : 1
     * regionScale : 0
     * provinceRegionId : 440000
     * cityRegionId : 440300
     * districtRegionId : 440304
     * roundAmount : 1
     * episodeAmount : 6
     * enrollStartTime : 1506067200000
     * enrollStopTime : 1506065400000
     * startTime : 1506070800000
     * stopTime : 1506074100000
     * enrollBroadcastName : 测试篮球积分赛报名广播
     * description : 测试篮球积分赛的比赛描述
     * descriptionSupplier : null
     * descriptionCustomer : 测试篮球积分赛的用户描述
     * pic : kidoo/2017-09-22/15-42/mv (3).jpg
     * picCustomer : kidoo/2017-09-22/15-42/mv (2).jpg
     * picSupplier : null
     * workerId : 1
     * status : 4
     * createTime : 1506066284000
     * roundList : null
     * enrollPlayerList : null
     * enrollSupplierList : null
     */

    private int id;
    private int awardId;
    private int channelAId;
    private int channelBId;
    private int channelCId;
    private String name;
    private int charge;
    private int playerAmount;
    private int teamAmount;
    private int supplierAmount;
    private int firstRoundArea;
    private int level;
    private int playerType;
    private int enrollType;
    private int format;
    private int regionScale;
    private int provinceRegionId;
    private int cityRegionId;
    private int districtRegionId;
    private int roundAmount;
    private int episodeAmount;
    private long enrollStartTime;
    private long enrollStopTime;
    private long startTime;
    private long stopTime;
    private String enrollBroadcastName;
    private String description;
    private Object descriptionSupplier;
    private String descriptionCustomer;
    private String pic;
    private String picCustomer;
    private Object picSupplier;
    private int workerId;
    private int status;
    private long createTime;
    private Object roundList;
    private Object enrollPlayerList;
    private Object enrollSupplierList;

    public void setId(int id) {
        this.id = id;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }

    public void setChannelAId(int channelAId) {
        this.channelAId = channelAId;
    }

    public void setChannelBId(int channelBId) {
        this.channelBId = channelBId;
    }

    public void setChannelCId(int channelCId) {
        this.channelCId = channelCId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
    }

    public void setTeamAmount(int teamAmount) {
        this.teamAmount = teamAmount;
    }

    public void setSupplierAmount(int supplierAmount) {
        this.supplierAmount = supplierAmount;
    }

    public void setFirstRoundArea(int firstRoundArea) {
        this.firstRoundArea = firstRoundArea;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public void setEnrollType(int enrollType) {
        this.enrollType = enrollType;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public void setRegionScale(int regionScale) {
        this.regionScale = regionScale;
    }

    public void setProvinceRegionId(int provinceRegionId) {
        this.provinceRegionId = provinceRegionId;
    }

    public void setCityRegionId(int cityRegionId) {
        this.cityRegionId = cityRegionId;
    }

    public void setDistrictRegionId(int districtRegionId) {
        this.districtRegionId = districtRegionId;
    }

    public void setRoundAmount(int roundAmount) {
        this.roundAmount = roundAmount;
    }

    public void setEpisodeAmount(int episodeAmount) {
        this.episodeAmount = episodeAmount;
    }

    public void setEnrollStartTime(long enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public void setEnrollStopTime(long enrollStopTime) {
        this.enrollStopTime = enrollStopTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public void setEnrollBroadcastName(String enrollBroadcastName) {
        this.enrollBroadcastName = enrollBroadcastName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionSupplier(Object descriptionSupplier) {
        this.descriptionSupplier = descriptionSupplier;
    }

    public void setDescriptionCustomer(String descriptionCustomer) {
        this.descriptionCustomer = descriptionCustomer;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setPicCustomer(String picCustomer) {
        this.picCustomer = picCustomer;
    }

    public void setPicSupplier(Object picSupplier) {
        this.picSupplier = picSupplier;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setRoundList(Object roundList) {
        this.roundList = roundList;
    }

    public void setEnrollPlayerList(Object enrollPlayerList) {
        this.enrollPlayerList = enrollPlayerList;
    }

    public void setEnrollSupplierList(Object enrollSupplierList) {
        this.enrollSupplierList = enrollSupplierList;
    }

    public int getId() {
        return id;
    }

    public int getAwardId() {
        return awardId;
    }

    public int getChannelAId() {
        return channelAId;
    }

    public int getChannelBId() {
        return channelBId;
    }

    public int getChannelCId() {
        return channelCId;
    }

    public String getName() {
        return name;
    }

    public int getCharge() {
        return charge;
    }

    public int getPlayerAmount() {
        return playerAmount;
    }

    public int getTeamAmount() {
        return teamAmount;
    }

    public int getSupplierAmount() {
        return supplierAmount;
    }

    public int getFirstRoundArea() {
        return firstRoundArea;
    }

    public int getLevel() {
        return level;
    }

    public int getPlayerType() {
        return playerType;
    }

    public int getEnrollType() {
        return enrollType;
    }

    public int getFormat() {
        return format;
    }

    public int getRegionScale() {
        return regionScale;
    }

    public int getProvinceRegionId() {
        return provinceRegionId;
    }

    public int getCityRegionId() {
        return cityRegionId;
    }

    public int getDistrictRegionId() {
        return districtRegionId;
    }

    public int getRoundAmount() {
        return roundAmount;
    }

    public int getEpisodeAmount() {
        return episodeAmount;
    }

    public long getEnrollStartTime() {
        return enrollStartTime;
    }

    public long getEnrollStopTime() {
        return enrollStopTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public String getEnrollBroadcastName() {
        return enrollBroadcastName;
    }

    public String getDescription() {
        return description;
    }

    public Object getDescriptionSupplier() {
        return descriptionSupplier;
    }

    public String getDescriptionCustomer() {
        return descriptionCustomer;
    }

    public String getPic() {
        return pic;
    }

    public String getPicCustomer() {
        return picCustomer;
    }

    public Object getPicSupplier() {
        return picSupplier;
    }

    public int getWorkerId() {
        return workerId;
    }

    public int getStatus() {
        return status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public Object getRoundList() {
        return roundList;
    }

    public Object getEnrollPlayerList() {
        return enrollPlayerList;
    }

    public Object getEnrollSupplierList() {
        return enrollSupplierList;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", awardId=" + awardId +
                ", channelAId=" + channelAId +
                ", channelBId=" + channelBId +
                ", channelCId=" + channelCId +
                ", name='" + name + '\'' +
                ", charge=" + charge +
                ", playerAmount=" + playerAmount +
                ", teamAmount=" + teamAmount +
                ", supplierAmount=" + supplierAmount +
                ", firstRoundArea=" + firstRoundArea +
                ", level=" + level +
                ", playerType=" + playerType +
                ", enrollType=" + enrollType +
                ", format=" + format +
                ", regionScale=" + regionScale +
                ", provinceRegionId=" + provinceRegionId +
                ", cityRegionId=" + cityRegionId +
                ", districtRegionId=" + districtRegionId +
                ", roundAmount=" + roundAmount +
                ", episodeAmount=" + episodeAmount +
                ", enrollStartTime=" + enrollStartTime +
                ", enrollStopTime=" + enrollStopTime +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", enrollBroadcastName='" + enrollBroadcastName + '\'' +
                ", description='" + description + '\'' +
                ", descriptionSupplier=" + descriptionSupplier +
                ", descriptionCustomer='" + descriptionCustomer + '\'' +
                ", pic='" + pic + '\'' +
                ", picCustomer='" + picCustomer + '\'' +
                ", picSupplier=" + picSupplier +
                ", workerId=" + workerId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", roundList=" + roundList +
                ", enrollPlayerList=" + enrollPlayerList +
                ", enrollSupplierList=" + enrollSupplierList +
                '}';
    }
}
