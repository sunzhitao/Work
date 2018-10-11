package com.szt.aop.processor;



import com.szt.aop.anno.MyAspect;
import com.szt.aop.anno.advice.Advice;
import com.szt.aop.util.PackageUtil;
import com.szt.ioc.IocContainer;
import com.szt.ioc.core.BeanDefinition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Company:中智关爱通(上海)
 *
 * @author：tao.zhang
 * @Date：Created in 14:23 2018/9/27
 */
public class AspectProcessor {

    public static Map<Method, String> aspectMap = new HashMap<>();
    public static List<String> packageScans = new ArrayList<>(8);


    public static List<String> aspectInit(Map<String, Object> aspectBeanMap) throws Exception {
        //获取所有的aspect里面的pointcut
        for (String s : aspectBeanMap.keySet()) {
            Object o = aspectBeanMap.get(s);
            //通过对象获取所有的pointcut
            MyAspect annotation = o.getClass().getAnnotation(MyAspect.class);
            if (annotation != null) {
                packageScans.add(annotation.pointcut());
                List<Class<?>> classList = PackageUtil.getClass(annotation.pointcut(), true);
                for (Class<?> aClass : classList) {
                    if (aClass.isAnnotationPresent(MyAspect.class)) {
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setObject(aClass.newInstance());
//                    Enhancer enhancer = new Enhancer();
//                    Callback[] callbacks = DefaultCallbackFilter.callbacks;
//                    enhancer.setCallbacks(callbacks);
//                    enhancer.setCallbackFilter(new DefaultCallbackFilter(aClass));
//                    Object object = enhancer.create();
//                    beanDefinition.setProxyObject(object);
                        IocContainer.beanDefinitionMap.put(aClass.getSimpleName(), beanDefinition);
                    }
                }
            }
        }
        return packageScans;
    }


    public static void init() throws Exception {
        //获取项目中所有的切面
        Map<String, Object> aspectBeanMap = IocContainer.aspectBeanMap;
        aspectInit(aspectBeanMap);
        for (String packageScan : packageScans) {
            List<Class<?>> classList = PackageUtil.getClass(packageScan, true);
            for (Class<?> aClass : classList) {
                Method[] declaredMethods = aClass.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    if (declaredMethod.isAnnotationPresent(Advice.class)) {
                        Advice annotation = declaredMethod.getAnnotation(Advice.class);
                        aspectMap.put(declaredMethod, annotation.value());
                    }
                }
            }
        }
    }
}
