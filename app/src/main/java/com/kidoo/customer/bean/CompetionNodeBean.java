package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 14:32
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionNodeBean implements Serializable{

    /**
     * id : 12
     * matchId : 17
     * msg : 报名完成，关闭报名
     * picMini : Fi48KJZOHM6zNDMBXYoepCBsQkIG.jpg
     * pic : Fi48KJZOHM6zNDMBXYoepCBsQkIG.jpg
     * timePoint : 1513226580000
     * createTime : 1513226619000
     */

    private int id;
    private int matchId;
    private String msg;
    private String picMini;
    private String pic;
    private long timePoint;
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
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

    public long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(long timePoint) {
        this.timePoint = timePoint;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
