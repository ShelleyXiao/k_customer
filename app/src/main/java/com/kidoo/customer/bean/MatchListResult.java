package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 11:51
 * Company: zx
 * Description:
 * FIXME
 */


public class MatchListResult implements Serializable {

    private List<MatchBean> matchList;

    public List<MatchBean> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<MatchBean> matchList) {
        this.matchList = matchList;
    }
}
