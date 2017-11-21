package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shelley on 2017/10/15.
 */

public class Broadcast implements Serializable {


    /**
     * id : 110
     * groupId : 27966276960257
     * name : 1
     * featurePic : kidoo/2017-09-22/15-42/mv(1).jpg
     * channelAId : 3
     * channelBId : 4
     * channelCId : 4
     * longitude : 114.049295
     * latitude : 22.564002
     * status : 1
     * customerId : null
     * supplierId : 1
     * shopId : null
     * type : 3
     * description : 测试篮球积分赛
     * address : 广东省深圳市福田区景田东路99号六单元501
     * charge : 1
     * provinceRegionId : 440000
     * cityRegionId : 440300
     * districtRegionId : 440304
     * campaignId : 28
     * roundId : 46
     * episodeId : 90
     * startTime : 1506064200000
     * stopTime : 1506071700000
     * createTime : 1506069180000
     * updateTime : 1506069180000
     * playerList : null
     */

    private int id;
    private long groupId;
    private String name;
    private String featurePic;
    private int channelAId;
    private int channelBId;
    private int channelCId;
    private double longitude;
    private double latitude;
    private int status;
    private int customerId;
    private int supplierId;
    private int shopId;
    private int type;
    private String description;
    private String address;
    private int charge;
    private int provinceRegionId;
    private int cityRegionId;
    private int districtRegionId;
    private int campaignId;
    private int roundId;
    private int episodeId;
    private long startTime;
    private long stopTime;
    private long createTime;
    private long updateTime;
    private List<Player> playerList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeaturePic() {
        return featurePic;
    }

    public void setFeaturePic(String featurePic) {
        this.featurePic = featurePic;
    }

    public int getChannelAId() {
        return channelAId;
    }

    public void setChannelAId(int channelAId) {
        this.channelAId = channelAId;
    }

    public int getChannelBId() {
        return channelBId;
    }

    public void setChannelBId(int channelBId) {
        this.channelBId = channelBId;
    }

    public int getChannelCId() {
        return channelCId;
    }

    public void setChannelCId(int channelCId) {
        this.channelCId = channelCId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getProvinceRegionId() {
        return provinceRegionId;
    }

    public void setProvinceRegionId(int provinceRegionId) {
        this.provinceRegionId = provinceRegionId;
    }

    public int getCityRegionId() {
        return cityRegionId;
    }

    public void setCityRegionId(int cityRegionId) {
        this.cityRegionId = cityRegionId;
    }

    public int getDistrictRegionId() {
        return districtRegionId;
    }

    public void setDistrictRegionId(int districtRegionId) {
        this.districtRegionId = districtRegionId;
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

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public String toString() {
        return "Broadcast{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", featurePic='" + featurePic + '\'' +
                ", channelAId=" + channelAId +
                ", channelBId=" + channelBId +
                ", channelCId=" + channelCId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", status=" + status +
                ", customerId=" + customerId +
                ", supplierId=" + supplierId +
                ", shopId=" + shopId +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", charge=" + charge +
                ", provinceRegionId=" + provinceRegionId +
                ", cityRegionId=" + cityRegionId +
                ", districtRegionId=" + districtRegionId +
                ", campaignId=" + campaignId +
                ", roundId=" + roundId +
                ", episodeId=" + episodeId +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", playerList=" + playerList +
                '}';
    }
}
