package com.kidoo.customer.api.http;

/**
 * User: ShaudXiao
 * Date: 2017-10-09
 * Time: 16:38
 * Company: zx
 * Description:
 * FIXME
 */


public class Utils {

    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

}
