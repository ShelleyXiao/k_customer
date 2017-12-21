package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-11-29
 * Time: 10:35
 * Company: zx
 * Description:
 * FIXME
 */


public class AllChannelResultBean implements Serializable {

    private List<ChannelA> channelAList;

    private List<ChannelCMap> channelCmaps;


    public List<ChannelCMap> getChannelCmaps() {
        return channelCmaps;
    }

    public void setChannelCmaps(List<ChannelCMap> channelCmaps) {
        this.channelCmaps = channelCmaps;
    }

    public List<ChannelA> getChannelAList() {
        return channelAList;
    }

    public void setChannelAList(List<ChannelA> channelAList) {
        this.channelAList = channelAList;
    }
}
