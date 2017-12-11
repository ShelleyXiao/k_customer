package com.kidoo.customer.bean;

/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 16:39
 * Company: zx
 * Description:
 * FIXME
 */


public class NewsBean {


    /**
     * id : 4
     * title : 夏天过去了冬天还会久吗?
     * titlePic : FlKGwa_JdRZB9SeArlBCndsTCzgf.jpg
     * introduction : 夏天
     * htmlContent : null
     * weight : 1
     * state : 1
     * committerId : null
     * committerType : null
     * createTime : null
     * updateTime : 1512877308000
     */

    private int id;
    private String title;
    private String titlePic;
    private String introduction;
    private String htmlContent;
    private int weight;
    private int state;
    private String committerId;
    private String committerType;
    private String createTime;
    private long updateTime;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitlePic(String titlePic) {
        this.titlePic = titlePic;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
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

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitlePic() {
        return titlePic;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getHtmlContent() {
        return htmlContent;
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

    public String getCreateTime() {
        return createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }
}
