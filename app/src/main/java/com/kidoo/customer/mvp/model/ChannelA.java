package com.kidoo.customer.mvp.model;

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
     * nameCh : 线下娱乐
     * nameEn : offline amuse
     * description : 线下娱乐
     * createTime : 1502953765000
     * channelBList : [{"id":1,"channelAId":1,"nameCh":"桌游","nameEn":"table game","description":"桌游","createTime":1502953831000,"channelCList":[{"id":1,"channelAId":1,"channelBId":1,"pic":"Fuz-RKpyeKRPsv6OvG60wbDAmgpJ.png","nameCh":"狼人杀","nameEn":"were wolf","status":0,"minimum":12,"maximum":12,"playerType":0,"durationMin":20,"durationMax":10080,"description":"狼人杀","createTime":null,"dimensionList":[]}]}]
     */

    private int id;
    private String nameCh;
    private String nameEn;
    private String description;
    private long createTime;
    private List<ChannelB> channelBList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<ChannelB> getChannelBList() {
        return channelBList;
    }

    public void setChannelBList(List<ChannelB> channelBList) {
        this.channelBList = channelBList;
    }

}
