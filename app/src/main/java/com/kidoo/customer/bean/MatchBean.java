package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 11:50
 * Company: zx
 * Description:
 * FIXME
 */


public class MatchBean implements Serializable {


    /**
     * id : 15
     * channelCId : 4
     * name : 测试
     * level : 1
     * format : 1
     * charge : null
     * msg : 测试
     * picMini : FjR22N69FKVZkyWi5a71Jmd9L_H-.JPG
     * pic : FjR22N69FKVZkyWi5a71Jmd9L_H-.JPG
     * committerId : 1
     * committerType : 1
     * state : 1
     * createTime : 1513094400000
     */

    private int id;
    private int channelCId;
    private String name;
    private int level;
    private int format;
    private Object charge;
    private String msg;
    private String picMini;
    private String pic;
    private int committerId;
    private int committerType;
    private int state;
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public Object getCharge() {
        return charge;
    }

    public void setCharge(Object charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public int getCommitterId() {
        return committerId;
    }

    public void setCommitterId(int committerId) {
        this.committerId = committerId;
    }

    public int getCommitterType() {
        return committerType;
    }

    public void setCommitterType(int committerType) {
        this.committerType = committerType;
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
}
