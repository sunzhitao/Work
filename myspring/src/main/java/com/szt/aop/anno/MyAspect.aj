package com.szt.aop.anno;

import java.lang.annotation.*;

/**
 * @author: sunzhitao
 * @date: 2018/10/11 10:17
 * @description:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyAspect {
     String id();
    String pointcut() default "";

}
