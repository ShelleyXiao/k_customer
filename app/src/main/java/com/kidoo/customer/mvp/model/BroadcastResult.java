package com.kidoo.customer.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shelley on 2017/10/15.
 */

public class BroadcastResult implements Serializable {

    int myBroadcastStatus;
    List<Broadcast> broadcastList;

    public int getMyBroadcastStatus() {
        return myBroadcastStatus;
    }

    public void setMyBroadcastStatus(int myBroadcastStatus) {
        this.myBroadcastStatus = myBroadcastStatus;
    }

    public List<Broadcast> getBroadcastList() {
        return broadcastList;
    }

    public void setBroadcastList(List<Broadcast> broadcastList) {
        this.broadcastList = broadcastList;
    }
}
