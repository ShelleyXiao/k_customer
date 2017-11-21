package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-12
 * Time: 19:50
 * Company: zx
 * Description: 战绩 （取名与服务端一样）
 * FIXME
 */


public class Dimension implements Serializable {

    /**
     * id : 1
     * channelCId : 6
     * name : 得分
     * valType : 0
     * description : 足球的得分
     * createTime : null
     */

    private int id;
    private int channelCId;
    private String name;
    private int valType;
    private String description;
    private Object createTime;

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

    public int getValType() {
        return valType;
    }

    public void setValType(int valType) {
        this.valType = valType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }
}
