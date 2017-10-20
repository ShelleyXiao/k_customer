package com.kidoo.customer.mvp.model;

import com.kidoo.customer.adapter.myCampaignAdapter.model.ICampaignMode;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 13:52
 * Company: zx
 * Description:
 * FIXME
 */


public class Teambase implements Serializable ,ICampaignMode{


    /**
     * id : 5
     * clubId : null
     * channelCId : 4
     * name : 篮球测试队-阿强
     * icon : FnlIQqycz7f0D8Fuh0aLf3RDRKoB.png
     * captainId : 1
     * status : 0
     * createTime : 1504766455000
     * updateTime : 1504766455000
     * memberList : null
     * medalList : null
     * customerList : null
     */

    private int id;
    private Object clubId;
    private int channelCId;
    private String name;
    private String icon;
    private int captainId;
    private int status;
    private long createTime;
    private long updateTime;
    private Object memberList;
    private Object medalList;
    private Object customerList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getClubId() {
        return clubId;
    }

    public void setClubId(Object clubId) {
        this.clubId = clubId;
    }

    public int getChannelCId() {
        return channelCId;
    }

    public void setChannelCId(int channelCId) {
        this.channelCId = channelCId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCaptainId() {
        return captainId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
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

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Object getMemberList() {
        return memberList;
    }

    public void setMemberList(Object memberList) {
        this.memberList = memberList;
    }

    public Object getMedalList() {
        return medalList;
    }

    public void setMedalList(Object medalList) {
        this.medalList = medalList;
    }

    public Object getCustomerList() {
        return customerList;
    }

    public void setCustomerList(Object customerList) {
        this.customerList = customerList;
    }

    @Override
    public String toString() {
        return "Teambase{" +
                "id=" + id +
                ", clubId=" + clubId +
                ", channelCId=" + channelCId +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", captainId=" + captainId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", memberList=" + memberList +
                ", medalList=" + medalList +
                ", customerList=" + customerList +
                '}';
    }
}
