package com.oliwen.util;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class PollingUtils {

    private static final int DEFAULT_POLLING_INTERVAL = 1000; // 默认轮询间隔1秒

    /**
     * 轮询调用查询接口直到接口返回期望状态或轮询次数用完。
     *
     * @param callable      查询接口的Callable实现
     * @param expectedState 期望状态
     * @param maxAttempts   最大轮询次数
     * @param interval      轮询间隔时间，单位毫秒
     * @param <T>           返回类型
     * @return 查询结果
     * @throws Exception 如果超时或其他异常
     */
    public static <T> T poll (Callable<T> callable, Predicate<T> expectedState, int maxAttempts, int interval) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            T result = null;
            for (int i = 0; i < maxAttempts; i++) {
                Future<T> future = executor.submit(callable);
                 result = future.get(interval, TimeUnit.MILLISECONDS);
                if (expectedState.test(result)) {
                    return result;
                }
                // 可以在这里添加日志记录
            }
            return result;
            //throw new TimeoutException("Reached max attempts without satisfying the expected state.");
        } finally {
            executor.shutdownNow();
        }
    }

    public static <T> T poll (Callable<T> callable, Predicate<T> expectedState, int maxAttempts) throws Exception {
        return poll(callable, expectedState, maxAttempts, DEFAULT_POLLING_INTERVAL);
    }

    public static <T> T poll (Callable<T> callable, Predicate<T> expectedState) throws Exception {
        return poll(callable, expectedState, 30, DEFAULT_POLLING_INTERVAL);
    }

    // 使用示例
    public static class StatusChecker {
        public static String checkStatus () {
            double random = Math.random();
            System.out.println("调用查询");
            // 模拟查询操作
            return random > 0.1 && random< 0.2 ? "COMPLETED":"ERROR";
        }
    }

}
