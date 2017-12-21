package com.kidoo.customer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-21
 * Time: 14:55
 * Company: zx
 * Description:
 * FIXME
 */


public class MyMatchsResult implements Serializable {

    List<MatchBean> matchList;

    public List<MatchBean> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<MatchBean> matchList) {
        this.matchList = matchList;
    }


}
