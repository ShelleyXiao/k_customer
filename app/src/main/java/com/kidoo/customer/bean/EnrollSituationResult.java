package com.kidoo.customer.bean;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-20
 * Time: 10:21
 * Company: zx
 * Description:
 * FIXME
 */


public class EnrollSituationResult {

    List<CompetionEnrollbean> enrollTeamList;
    List<EnrollCustomerBean> enrollCustomerList;

    public void setEnrollTeamList(List<CompetionEnrollbean> enrollTeamList) {
        this.enrollTeamList = enrollTeamList;
    }

    public void setEnrollCustomerList(List<EnrollCustomerBean> enrollCustomerList) {
        this.enrollCustomerList = enrollCustomerList;
    }

    public List<CompetionEnrollbean> getEnrollTeamList() {
        return enrollTeamList;
    }

    public List<EnrollCustomerBean> getEnrollCustomerList() {
        return enrollCustomerList;
    }
}
