package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 14:09
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamsBaseInfoResult implements Serializable {

    private List<TeamBean> teamList;

    public List<TeamBean> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamBean> teamList) {
        this.teamList = teamList;
    }
}
