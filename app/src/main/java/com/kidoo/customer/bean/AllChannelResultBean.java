package com.kidoo.customer.bean;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-11-29
 * Time: 10:35
 * Company: zx
 * Description:
 * FIXME
 */


public class AllChannelResultBean implements Serializable{

    private ChannelCMap channelCmap;
    private ChannelA channelAList;

    public ChannelCMap getChannelCmap() {
        return channelCmap;
    }

    public void setChannelCmap(ChannelCMap channelCmap) {
        this.channelCmap = channelCmap;
    }

    public ChannelA getChannelAList() {
        return channelAList;
    }

    public void setChannelAList(ChannelA channelAList) {
        this.channelAList = channelAList;
    }
}
