package com.kidoo.customer.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-23
 * Time: 20:21
 * Company: zx
 * Description:
 * FIXME
 */


public class DetailResult implements Serializable{

    private Broadcast broadcast;
    private List<Player> playerList;

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }
}
