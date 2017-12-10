package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shelley on 2017/12/9.
 */

public class ArenaListResult implements Serializable {

    private List<AreanaBean> arenaList;

    public List<AreanaBean> getArenaList() {
        return arenaList;
    }

    public void setArenaList(List<AreanaBean> arenaList) {
        this.arenaList = arenaList;
    }
}
