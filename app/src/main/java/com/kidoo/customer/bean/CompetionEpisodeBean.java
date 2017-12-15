package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 14:48
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionEpisodeBean implements Serializable {


    /**
     * id : 28
     * matchId : 17
     * name : 啊
     * msg : 啊
     * state : 0
     * startTime : 1513416420000
     * stopTime : 1513416540000
     * createTime : null
     */

    private int id;
    private int matchId;
    private String name;
    private String msg;
    private int state;
    private long startTime;
    private long stopTime;
    private Object createTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CompetionEpisodeBean{" +
                "id=" + id +
                ", matchId=" + matchId +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", state=" + state +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", createTime=" + createTime +
                '}';
    }
}
