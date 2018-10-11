package com.szt.aop;


import com.szt.aop.anno.advice.*;
import com.szt.aop.processor.AspectProcessor;
import com.szt.ioc.IocContainer;
import com.szt.ioc.core.BeanDefinition;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Company:中智关爱通(上海)
 *
 * @author：tao.zhang
 * @Date：Created in 16:40 2018/9/26
 */
public class AopCallback implements MethodInterceptor {

    public static final AopCallback INSTANCE = new AopCallback();

    private AopCallback() {
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        //判断当前对象是否被拦截
        List<String> packageScans = AspectProcessor.packageScans;
        String name = obj.getClass().getPackage().getName();
        //当前的类是否在拦截范围内
        boolean flag = false;
        for (String packageScan : packageScans) { flag= name.startsWith(packageScan);}
        if (flag && method.isAnnotationPresent(Advice.class)) {
            //拦截器处理
            return interceptHandle(obj, method, args, methodProxy);
        }
        return methodProxy.invokeSuper(obj, args);
    }


    private Object interceptHandle(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object o = null;
        Map<Method, String> aspectMap = AspectProcessor.aspectMap;
        if (aspectMap.size() == 0) {
            return methodProxy.invokeSuper(obj, args);
        }
        String arg = aspectMap.get(method);
        if (arg.equals("") || arg == null) {
            return methodProxy.invokeSuper(obj, args);
        }
        Object object = IocContainer.beanDefinitionMap.get(arg);
        Method[] declaredMethods = object.getClass().getMethods();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method declaredMethod : declaredMethods) {
            //前置通知
            if (declaredMethod.isAnnotationPresent(Before.class)) {
                methodMap.put("Before", declaredMethod);
                //环绕通知
            } else if (declaredMethod.isAnnotationPresent(AfterThrowing.class)) {
                methodMap.put("AfterThrowing", declaredMethod);
                //环绕通知
            } else if (declaredMethod.isAnnotationPresent(Around.class)) {
                Method aroundMethod = object.getClass().getMethod(declaredMethod.getName(), Object.class, Object[].class, MethodProxy.class);
                methodMap.put("Around", aroundMethod);
                //返回后通知
            } else if (declaredMethod.isAnnotationPresent(AfterReturning.class)) {
                methodMap.put("AfterReturning", declaredMethod);
                //返回后通知
            } else if (declaredMethod.isAnnotationPresent(After.class)) {
                methodMap.put("After", declaredMethod);
            }
        }

        //前置通知
        if (methodMap.get("Before") != null) {
            //如果是private修饰符的，则把可访问性设置为true
            Method method1 = methodMap.get("Before");
            if (!method1.isAccessible()) {
                method1.setAccessible(true);
            }
            method1.invoke(object);
        }
        //环绕通知


        //目标方法
        try {
            if (methodMap.get("Around") != null) {
                //如果是private修饰符的，则把可访问性设置为true
                Method method1 = methodMap.get("Around");
                if (!method1.isAccessible()) {
                    method1.setAccessible(true);
                }
                o = method1.invoke(object,obj,args,methodProxy);
            } else {
                o = methodProxy.invokeSuper(obj, args);
            }
        } catch (Exception e) {
            //被拦截方法
            if (methodMap.get("AfterThrowing") != null) {
                //如果是private修饰符的，则把可访问性设置为true
                Method method1 = methodMap.get("AfterThrowing");
                if (!method1.isAccessible()) {
                    method1.setAccessible(true);
                }
                method1.invoke(object);
            } else {
                e.printStackTrace();
            }
        }
//        //环绕通知
//        if (methodMap.get("Around") != null) {
//            methodMap.get("Around").invoke(proxyObject);
//        }
        //返回后通知
        if (methodMap.get("AfterReturning") != null) {
            //如果是private修饰符的，则把可访问性设置为true
            Method method1 = methodMap.get("AfterReturning");
            if (!method1.isAccessible()) {
                method1.setAccessible(true);
            }
            method1.invoke(object);
        }
        return o;
    }
}
