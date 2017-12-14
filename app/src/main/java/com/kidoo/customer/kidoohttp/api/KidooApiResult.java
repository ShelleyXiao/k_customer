package com.kidoo.customer.kidoohttp.api;

import com.kidoo.customer.bean.PageInfo;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 15:59
 * Company: zx
 * Description:
 * FIXME
 */


public class KidooApiResult<T> implements Serializable {
    boolean success;
    private String errorCode;
    private String errorMsg;
    private int showType;
    private T data;

    private PageInfo pageInfo;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "KidooApiResult{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", showType=" + showType +
                ", data=" + data +
                ", pageInfo=" + pageInfo +
                '}';
    }
}
