package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shelley on 2017/12/9.
 */

public class AreanaBean implements Serializable {


    /**
     * id : 1
     * shopId : null
     * contact : 暂无
     * name : 深圳中学篮球场
     * picMini : FhILfrqvtCP92auQm-jgDrGlD47-.jpg
     * pic : FhILfrqvtCP92auQm-jgDrGlD47-.jpg
     * msg : 免费全天对外开放。
     * type : 2
     * chargeType : null
     * level : 1
     * address : 广东省深圳市罗湖区凉果街40号
     * longitude : 114.124937
     * latitude : 22.561257
     * districtRegionId : null
     * committerId : null
     * committerType : null
     * state : 1
     * createTime : null
     * updateTime : null
     * channelCList : []
     */

    private int id;
    private String shopId;
    private String contact;
    private String name;
    private String picMini;
    private String pic;
    private String msg;
    private int type;
    private String chargeType;
    private int level;
    private String address;
    private double longitude;
    private double latitude;
    private String districtRegionId;
    private String committerId;
    private String committerType;
    private int state;
    private String createTime;
    private String updateTime;
    private List<ChannelC> channelCList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDistrictRegionId() {
        return districtRegionId;
    }

    public void setDistrictRegionId(String districtRegionId) {
        this.districtRegionId = districtRegionId;
    }

    public String getCommitterId() {
        return committerId;
    }

    public void setCommitterId(String committerId) {
        this.committerId = committerId;
    }

    public String getCommitterType() {
        return committerType;
    }

    public void setCommitterType(String committerType) {
        this.committerType = committerType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<ChannelC> getChannelCList() {
        return channelCList;
    }

    public void setChannelCList(List<ChannelC> channelCList) {
        this.channelCList = channelCList;
    }

    @Override
    public String toString() {
        return "AreanaBean{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", contact='" + contact + '\'' +
                ", name='" + name + '\'' +
                ", picMini='" + picMini + '\'' +
                ", pic='" + pic + '\'' +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                ", chargeType='" + chargeType + '\'' +
                ", level=" + level +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", districtRegionId='" + districtRegionId + '\'' +
                ", committerId='" + committerId + '\'' +
                ", committerType='" + committerType + '\'' +
                ", state=" + state +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
