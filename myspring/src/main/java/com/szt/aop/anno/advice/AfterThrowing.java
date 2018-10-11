package com.szt.aop.anno.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Company:中智关爱通(上海)
 *
 * @author：tao.zhang
 * @Date：Created in 15:27 2018/9/26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AfterThrowing {
    String value() default "";

    String pointcut() default "";

    String throwing() default "";

    String argNames() default "";
}
