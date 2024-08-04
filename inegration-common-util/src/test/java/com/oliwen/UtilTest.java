package com.oliwen;

import com.oliwen.util.PollingUtils;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class UtilTest {

    public static void main (String[] args) {
        Callable<String> callable = PollingUtils.StatusChecker::checkStatus;
        Predicate<String> expectedState = "COMPLETED"::equals;

        try {
            String result = PollingUtils.poll(callable, expectedState, 10, 2*1000);
            System.out.println("Status query completed with result: " + result);
        } catch (Exception e) {
            System.out.println("Polling failed: " + e.getMessage());
        }
//        double random = Math.random();
//        System.out.println(random);
    }
}
