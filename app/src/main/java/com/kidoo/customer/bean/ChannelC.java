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


public class ChannelC implements Serializable {


    /**
     * id : 5
     * channelAId : 3
     * channelBId : 4
     * pic : FqaKnpiFTiTJgLHSqeUrWKnvqG1m.png
     * nameCh : 篮球(3v3)
     * nameEn : basketball 3v3
     * status : 0
     * minimum : 6
     * maximum : 6
     * playerType : 1
     * durationMin : 10
     * durationMax : 10080
     * description : 接头篮球
     * createTime : null
     * dimensionList : []
     */

    private int id;
    private int channelAId;
    private int channelBId;
    private String pic;
    private String nameCh;
    private String nameEn;
    private int status;
    private int minimum;
    private int maximum;
    private int playerType;
    private int durationMin;
    private int durationMax;
    private String description;
    private long createTime;
    private List<Dimension> mDimensionList;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getPlayerType() {
        return playerType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public int getDurationMax() {
        return durationMax;
    }

    public void setDurationMax(int durationMax) {
        this.durationMax = durationMax;
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

    public List<Dimension> getDimensionList() {
        return mDimensionList;
    }

    public void setDimensionList(List<Dimension> dimensionList) {
        mDimensionList = dimensionList;
    }
}
