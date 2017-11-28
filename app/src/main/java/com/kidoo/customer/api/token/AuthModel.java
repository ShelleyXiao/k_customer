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

    private long getTokenTime;
    private long difTime;// nowtiem - serverTime = difTime
    private long serverTime;
    private String tokenId; //login 成功返回tokenId
    private String imPasswd;

    public long getGetTokenTime() {
        return getTokenTime;
    }

    public void setGetTokenTime(long getTokenTime) {
        this.getTokenTime = getTokenTime;
    }

    public long getDifTime() {
        return difTime;
    }

    public void setDifTime(long difTime) {
        this.difTime = difTime;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getImPasswd() {
        return imPasswd;
    }

    public void setImPasswd(String imPasswd) {
        this.imPasswd = imPasswd;
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
