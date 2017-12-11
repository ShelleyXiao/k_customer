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


public class ChannelC extends Channel implements Serializable {


    /**
     * id : 1
     * channelAId : 1
     * channelBId : 1
     * pic : FqaKnpiFTiTJgLHSqeUrWKnvqG1m.png
     * name : 3V3
     * state : 1
     * minTeam : 3
     * maxTeam : 6
     * minPlayer : 2
     * maxPlayer : 2
     * playerType : 1
     * description : 街头篮球
     * weight : 2
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
    private String createTime;

    private List<Dimension> dimensions;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }
}
