package com.kidoo.customer.api;

import com.kidoo.customer.api.http.model.ApiResult;
import com.kidoo.customer.bean.PageInfo;

/**
 * User: ShaudXiao
 * Date: 2017-10-10
 * Time: 15:59
 * Company: zx
 * Description:
 * FIXME
 */


public class KidooApiResult<T> extends ApiResult<T> {
    boolean success;
    private int errorCode;
    private String errorMsg;
    private int showType;
    private T data;

    private PageInfo pageInfo;



}
