/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kidoo.customer.kidoohttp.http.exception;

/**
 * User: ShaudXiao
 * Date: 2017-11-21
 * Time: 11:24
 * Company: zx
 * Description: 处理服务器异常
 * FIXME
 */
public class ServerException extends RuntimeException {
    private String errorCode;
    private String errorMsg;

    public ServerException(String errCode, String msg) {
        super(msg);
        this.errorCode = errCode;
        this.errorMsg = msg;
    }

    public String getErrCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}