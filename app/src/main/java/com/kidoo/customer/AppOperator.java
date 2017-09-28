package com.kidoo.customer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: ShaudXiao
 * Date: 2017-09-28
 * Time: 17:55
 * Company: zx
 * Description: 构造一个线程池，执行一些后台任务
 * FIXME
 */


public class AppOperator {

    private static ExecutorService EXECUTOR_SERVICE;

    public static ExecutorService getExecutor() {
        if(null == EXECUTOR_SERVICE) {
            synchronized (AppOperator.class) {
                if (EXECUTOR_SERVICE == null) {
                    EXECUTOR_SERVICE = Executors.newFixedThreadPool(6);
                }
            }
        }

        return EXECUTOR_SERVICE;
    }

    public static void runOnBackgrounThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }
}
