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


public class ChannelA implements Serializable {
    /**
     * id : 1
     * name : 体育
     * description : 所有体育的父类
     * createTime : null
     * weight : 0
     */

    private int id;
    private String name;
    private String description;
    private String createTime;
    private int weight;

    private List<ChannelB> channelBList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<ChannelB> getChannelBList() {
        return channelBList;
    }

    public void setChannelBList(List<ChannelB> channelBList) {
        this.channelBList = channelBList;
    }

    @Override
    public String toString() {
        return "ChannelA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", weight=" + weight +
                '}';
    }
}
