package com.oliwen.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * bean 获取工具类
 *
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2024/4/13 14:52
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
        setContent(applicationContext);
    }

    private static void setContent (ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean (Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    public static <T> T getBean (String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    public static String getId () {
        return context.getId();
    }
}
