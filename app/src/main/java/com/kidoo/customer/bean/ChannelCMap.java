package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-11-29
 * Time: 10:29
 * Company: zx
 * Description:
 * FIXME
 */


public class ChannelCMap implements Serializable {


    /**
     * id : 3
     * channelAId : 1
     * channelBId : 1
     * pic : FqaKnpiFTiTJgLHSqeUrWKnvqG1m.png
     * name : 篮球5v5
     * state : 1
     * minTeam : 5
     * maxTeam : 10
     * minPlayer : 2
     * maxPlayer : 2
     * playerType : 1
     * description : 5人篮球
     * weight : 1
     * createTime : null
     */

    private int id;
    private int channelAId;
    private int channelBId;
    private String pic;
    private String name;
    private int state;
    private int minTeam;
    private int maxTeam;
    private int minPlayer;
    private int maxPlayer;
    private int playerType;
    private String description;
    private int weight;
    private Object createTime;

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

    public int getChannelBId() {
        return channelBId;
    }

    public void setChannelBId(int channelBId) {
        this.channelBId = channelBId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMinTeam() {
        return minTeam;
    }

    public void setMinTeam(int minTeam) {
        this.minTeam = minTeam;
    }

    public int getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(int maxTeam) {
        this.maxTeam = maxTeam;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public int getPlayerType() {
        return playerType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
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

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ChannelCMap{" +
                "id=" + id +
                ", channelAId=" + channelAId +
                ", channelBId=" + channelBId +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", minTeam=" + minTeam +
                ", maxTeam=" + maxTeam +
                ", minPlayer=" + minPlayer +
                ", maxPlayer=" + maxPlayer +
                ", playerType=" + playerType +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", createTime=" + createTime +
                '}';
    }
}
