package com.kidoo.customer.bean;

/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 16:41
 * Company: zx
 * Description:
 * FIXME
 */


public class BannerBean {


    /**
     * id : 6
     * picMini : FlKGwa_JdRZB9SeArlBCndsTCzgf.jpg
     * pic : FlKGwa_JdRZB9SeArlBCndsTCzgf.jpg
     * content : null
     * type : 2
     * weight : 1
     * state : 1
     * committerId : null
     * committerType : null
     * createTime : 1512781053000
     * updateTime : 1512873405000
     */

    private int id;
    private String picMini;
    private String pic;
    private String content;
    private int type;
    private int weight;
    private int state;
    private String committerId;
    private String committerType;
    private long createTime;
    private long updateTime;

    public void setId(int id) {
        this.id = id;
    }

    public void setPicMini(String picMini) {
        this.picMini = picMini;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCommitterId(String committerId) {
        this.committerId = committerId;
    }

    public void setCommitterType(String committerType) {
        this.committerType = committerType;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public String getPicMini() {
        return picMini;
    }

    public String getPic() {
        return pic;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public int getState() {
        return state;
    }

    public String getCommitterId() {
        return committerId;
    }

    public String getCommitterType() {
        return committerType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }
}
