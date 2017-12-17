package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class TeamBean implements Serializable {


    /**
     * id : 4
     * clubId : null
     * channelCId : 4
     * name : 吃鸡0965
     * icon : Fvm6lIlLhg-XLqMCByIBQbZiG_GZ.png
     * captainId : 1
     * state : 1
     * createTime : 1512528853000
     * updateTime : 1512528853000
     * memberList : []
     */

    private int id;
    private Object clubId;
    private int channelCId;
    private String name;
    private String icon;
    private int captainId;
    private int state;
    private long createTime;
    private long updateTime;
    private List<?> memberList;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public List<?> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<?> memberList) {
        this.memberList = memberList;
    }

    @Override
    public String toString() {
        return "TeamBean{" +
                "id=" + id +
                ", clubId=" + clubId +
                ", channelCId=" + channelCId +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", captainId=" + captainId +
                ", state=" + state +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
