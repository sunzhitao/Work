package com.szt.ioc;


//import com.szt.aop.processor.AspectProcessor;
import com.szt.ioc.core.BeanFactory;
import com.szt.ioc.processor.IocProcessor;

import java.util.HashMap;
import java.util.Map;


public  class IocContainer {

    public static Map<String, Object> beanDefinitionMap = new HashMap<>();
    public static Map<String, Object> aspectBeanMap = new HashMap<>();


    public  static  void run(Class<?> clazz) throws Exception {
        //获取启动类的包名，作为扫描bean的地址
        String basePackage = clazz.getPackage().getName();
        init(basePackage);
    }

    public   static void init(String basePackage) throws Exception {
        //IOC
        BeanFactory beanFactory = IocProcessor.config(basePackage);
        beanDefinitionMap = beanFactory.getBeanMap();
        //AOP
        //获取项目中的切面的bean
       aspectBeanMap = beanFactory.getAspectBeanMap();
        //处理
//        AspectProcessor.init();



    }


}
