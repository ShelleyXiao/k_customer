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
     * id : 4
     * channelAId : 3
     * nameCh : 篮球
     * nameEn : basketball
     * description : 篮球
     * createTime : 1502955328000
     * channelCList : [{"id":4,"channelAId":3,"channelBId":4,"pic":"FqaKnpiFTiTJgLHSqeUrWKnvqG1m.png","nameCh":"篮球(5v5)","nameEn":"basketball 5v5","status":0,"minimum":10,"maximum":20,"playerType":1,"durationMin":10,"durationMax":10080,"description":"篮球正式赛","createTime":null,"dimensionList":[{"id":3,"channelCId":4,"name":"得分","valType":0,"description":"篮球的得分","createTime":null},{"id":4,"channelCId":4,"name":"助攻","valType":0,"description":"篮球的助攻","createTime":null}]},{"id":5,"channelAId":3,"channelBId":4,"pic":"FqaKnpiFTiTJgLHSqeUrWKnvqG1m.png","nameCh":"篮球(3v3)","nameEn":"basketball 3v3","status":0,"minimum":6,"maximum":6,"playerType":1,"durationMin":10,"durationMax":10080,"description":"接头篮球","createTime":null,"dimensionList":[]}]
     */

    private int id;
    private int channelAId;
    private String nameCh;
    private String nameEn;
    private String description;
    private long createTime;
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

    public List<ChannelC> getChannelCList() {
        return channelCList;
    }

    public void setChannelCList(List<ChannelC> channelCList) {
        this.channelCList = channelCList;
    }

}
