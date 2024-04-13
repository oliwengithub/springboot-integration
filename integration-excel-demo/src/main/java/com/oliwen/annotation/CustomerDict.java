package com.oliwen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义字典翻译处理
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2024/4/13 15:02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CustomerDict {

    /**
     * @return 字典编码
     */
    String value ();
}
