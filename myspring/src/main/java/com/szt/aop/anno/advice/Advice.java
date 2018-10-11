package com.szt.aop.anno.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Company:中智关爱通(上海)
 *
 * @author：tao.zhang
 * @Date：Created in 14:19 2018/9/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Advice {
    String value() default "";
}
