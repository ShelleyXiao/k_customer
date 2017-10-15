package com.kidoo.customer.mvp.model;

import java.io.Serializable;

/**
 * Created by Shelley on 2017/10/15.
 */

public class InitData implements Serializable {


    /**
     * qnToken : xb_TmXf7uGR6LYyeYqOI4UHPm5Iw2U_d7_5QnGvC:Q2VwPp36aRI0S-_cZmub5Yf-TP0=:eyJzYXZlS2V5IjoiJHtldGFnfSR7ZXh0fSIsInNjb3BlIjoia2lkb28iLCJkZWFkbGluZSI6MTUwODA2MTU4Mn0=
     * appNeedUpdate : false
     * webAppVersion : 1.0
     * qnDomain : http://ouhstqlop.bkt.clouddn.com/
     * qnTokenTime : 1508054382158
     */

    private String qnToken;
    private boolean appNeedUpdate;
    private String webAppVersion;
    private String qnDomain;
    private String qnTokenTime;

    public String getQnToken() {
        return qnToken;
    }

    public void setQnToken(String qnToken) {
        this.qnToken = qnToken;
    }

    public boolean isAppNeedUpdate() {
        return appNeedUpdate;
    }

    public void setAppNeedUpdate(boolean appNeedUpdate) {
        this.appNeedUpdate = appNeedUpdate;
    }

    public String getWebAppVersion() {
        return webAppVersion;
    }

    public void setWebAppVersion(String webAppVersion) {
        this.webAppVersion = webAppVersion;
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
}
