package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 17:34
 * Company: zx
 * Description:
 * FIXME
 */


public class TeambaseResult implements Serializable{

    private List<Teambase> teamList;

    public List<Teambase> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Teambase> teamList) {
        this.teamList = teamList;
    }
}
