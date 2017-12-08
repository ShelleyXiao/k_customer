package com.kidoo.customer.api.token;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-12-08
 * Time: 09:57
 * Company: zx
 * Description:
 * FIXME
 */


public class QNToken implements Serializable {

    private String qnToken;
    private String qnDomain;
    private String qnTokenTime;

    public String getQnToken() {
        return qnToken;
    }

    public void setQnToken(String qnToken) {
        this.qnToken = qnToken;
    }

    public String getQnDomain() {
        return qnDomain;
    }

    public void setQnDomain(String qnDomain) {
        this.qnDomain = qnDomain;
    }


    public String getQnTokenTime() {
        return qnTokenTime;
    }

    public void setQnTokenTime(String qnTokenTime) {
        this.qnTokenTime = qnTokenTime;
    }

    @Override
    public String toString() {
        return "QNToken{" +
                "qnToken='" + qnToken + '\'' +
                ", qnDomain='" + qnDomain + '\'' +
                ", qnTokenTime='" + qnTokenTime + '\'' +
                '}';
    }
}
