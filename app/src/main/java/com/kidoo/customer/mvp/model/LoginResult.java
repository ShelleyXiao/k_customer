package com.kidoo.customer.mvp.model;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 18:08
 * Company: zx
 * Description:
 * FIXME
 */


public class LoginResult implements Serializable {
    private Customer customer;
    private String tokenId;
    private long serverTime;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "customer=" + customer.toString() +
                ", tokenId='" + tokenId + '\'' +
                ", serverTime=" + serverTime +
                '}';
    }
}
