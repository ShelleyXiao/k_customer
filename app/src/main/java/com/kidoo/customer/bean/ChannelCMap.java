package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-11-29
 * Time: 10:29
 * Company: zx
 * Description:
 * FIXME
 */


public class ChannelCMap implements Serializable {

    private List<ChannelC> channelCList;

    public List<ChannelC> getChannelCList() {
        return channelCList;
    }

    public void setChannelCList(List<ChannelC> channelCList) {
        this.channelCList = channelCList;
    }
}
