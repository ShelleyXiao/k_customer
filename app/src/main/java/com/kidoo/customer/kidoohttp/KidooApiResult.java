package com.kidoo.customer.kidoohttp;

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
    private int errorCode;
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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
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
}
