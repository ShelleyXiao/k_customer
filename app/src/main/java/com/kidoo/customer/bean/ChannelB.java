package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-12
 * Time: 19:42
 * Company: zx
 * Description: 一级频道
 * FIXME
 */


public class ChannelB implements Serializable {


    /**
     * id : 1
     * channelAId : 1
     * name : 篮球
     * description : 所有篮球的父类
     * weight : 0
     * createTime : null
     */

    private int id;
    private int channelAId;
    private String name;
    private String description;
    private int weight;
    private String createTime;

    private List<ChannelC> channelCList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelAId() {
        return channelAId;
    }

    public void setChannelAId(int channelAId) {
        this.channelAId = channelAId;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ChannelC> getChannelCList() {
        return channelCList;
    }

    public void setChannelCList(List<ChannelC> channelCList) {
        this.channelCList = channelCList;
    }

    @Override
    public String toString() {
        return "ChannelB{" +
                "id=" + id +
                ", channelAId=" + channelAId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
