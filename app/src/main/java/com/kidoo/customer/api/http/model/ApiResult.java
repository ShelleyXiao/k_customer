package com.kidoo.customer.api.http.model;


import com.kidoo.customer.bean.PageInfo;

/**
 * User: ShaudXiao
 * Date: 2017-10-09
 * Time: 16:20
 * Company: zx
 * Description: 提供的默认的标注返回api
 * FIXME
 */

public class ApiResult<T> {
    boolean success;
    private String errorCode;
    private String errorMsg;
    private int showType;
    private T data;

    private PageInfo pageInfo;

    public String getCode() {
        return errorCode;
    }

    public void setCode(String code) {
        this.errorCode = code;
    }

    public String getMsg() {
        return errorMsg;
    }

    public void setMsg(String msg) {
        this.errorMsg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return success;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code='" + errorCode + '\'' +
                ", msg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
