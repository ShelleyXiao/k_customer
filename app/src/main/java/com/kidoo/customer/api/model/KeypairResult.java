package com.kidoo.customer.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * User: ShaudXiao
 * Date: 2017-10-09
 * Time: 10:33
 * Company: zx
 * Description:
 * FIXME
 */


public class KeypairResult {


    /**
     * success : true
     * errorCode : null
     * errorMsg : null
     * showType : 1
     * data : {"privateKey":"",
     *          "publicKey":""}
     * pageInfo : null
     */

    @SerializedName("success")
    private boolean success;

    @SerializedName("errorCode")
    private Object errorCode;

    @SerializedName("errorMsg")
    private Object errorMsg;

    @SerializedName("showType")
    private int showType;

    private DataBean data;


    private Object pageInfo;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setPageInfo(Object pageInfo) {
        this.pageInfo = pageInfo;
    }

    public boolean getSuccess() {
        return success;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public int getShowType() {
        return showType;
    }

    public DataBean getData() {
        return data;
    }

    public Object getPageInfo() {
        return pageInfo;
    }

    public static class DataBean {
        /**
         * privateKey :
         * publicKey :
         */

        @SerializedName("privateKey")
        private String privateKey;

        @SerializedName("publicKey")
        private String publicKey;

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }
    }
}
