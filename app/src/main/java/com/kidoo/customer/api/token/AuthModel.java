package com.kidoo.customer.api.token;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-12
 * Time: 14:30
 * Company: zx
 * Description:
 * FIXME
 */


public class AuthModel implements Serializable {


    private String difTime;// nowtiem - serverTime = difTime
    private String serverTime;
    private String tokenId; //login 成功返回tokenId




    public String getDifTime() {
        return difTime;
    }

    public void setDifTime(String difTime) {
        this.difTime = difTime;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }


    @Override
    public String toString() {
        return "AuthModel{" +
                ", difTime='" + difTime + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", tokenId='" + tokenId + '\'' +
                '}';
    }
}
