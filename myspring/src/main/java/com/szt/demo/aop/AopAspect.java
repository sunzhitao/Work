package com.szt.demo.aop;

import com.szt.aop.anno.MyAspect;
import com.szt.aop.anno.advice.*;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author: sunzhitao
 * @date: 2018/10/11 10:20
 * @description:
 */
@MyAspect(id="AopAspect",pointcut="com.szt")
public class AopAspect {

    @Before
    public void before(){
        System.out.println("-----------------------------AopAspect before-----------------------------");
    }
    @After
    public void after(){
        System.out.println("-----------------------------AopAspect after-----------------------------");
    }
    @AfterReturning
    public void afterReturning(){
        System.out.println("-----------------------------AopAspect afterReturning-----------------------------");
    }
    @AfterThrowing
    public void afterThrowing(){
        System.out.println("-----------------------------AopAspect afterThrowing-----------------------------");
    }

    @Around
    public Object around(Object obj,Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("-----------------------------AopAspect around start-----------------------------");
        Object o = methodProxy.invokeSuper(obj, args);
        System.out.println("-----------------------------AopAspect around end-----------------------------");
        return o;
    }
}
