package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * Created by Shelley on 2017/12/2.
 */

public class MedalBean implements Serializable {

    private int id ;
    private int awardId ;
    private int channelCId ;
    private int campaignId ;
    private int level ;
    private int roundId ;
    private int roundNo ;

    private String name;

    private double bonus;
    private String picMini;
    private String pic;
    private String descriptionMini;
    private String description;

    private int type;
    private int rankNo;
    private int medalType;
    private int electType;
    private int customerId;
    private int teamId;
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAwardId() {
        return awardId;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }

    public int getChannelCId() {
        return channelCId;
    }

    public void setChannelCId(int channelCId) {
        this.channelCId = channelCId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public String getPicMini() {
        return picMini;
    }

    public void setPicMini(String picMini) {
        this.picMini = picMini;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescriptionMini() {
        return descriptionMini;
    }

    public void setDescriptionMini(String descriptionMini) {
        this.descriptionMini = descriptionMini;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRankNo() {
        return rankNo;
    }

    public void setRankNo(int rankNo) {
        this.rankNo = rankNo;
    }

    public int getMedalType() {
        return medalType;
    }

    public void setMedalType(int medalType) {
        this.medalType = medalType;
    }

    public int getElectType() {
        return electType;
    }

    public void setElectType(int electType) {
        this.electType = electType;
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

    @Override
    public String toString() {
        return "MedalBean{" +
                "id=" + id +
                ", awardId=" + awardId +
                ", channelCId=" + channelCId +
                ", campaignId=" + campaignId +
                ", level=" + level +
                ", roundId=" + roundId +
                ", roundNo=" + roundNo +
                ", name='" + name + '\'' +
                ", bonus=" + bonus +
                ", picMini='" + picMini + '\'' +
                ", pic='" + pic + '\'' +
                ", descriptionMini='" + descriptionMini + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", rankNo=" + rankNo +
                ", medalType=" + medalType +
                ", electType=" + electType +
                ", customerId=" + customerId +
                ", teamId=" + teamId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
