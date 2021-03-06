package com.jsf.utils.annotation;

import java.lang.annotation.*;

/**
 * Created by xujunfei on 2017/8/1.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {

    boolean need() default true;

    boolean useCache() default true;

    String name() default "token";

    String type() default "header";

    /**
     * 额外字段
     */
    boolean transform() default false;

}
