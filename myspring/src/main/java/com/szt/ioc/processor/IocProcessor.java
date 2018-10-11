package com.szt.ioc.processor;


import com.szt.ioc.core.BeanFactory;

import java.util.Map;

public class IocProcessor {
    public static  BeanFactory config(String packageName) throws Exception {
        //创建一个bean工厂
        return   new BeanFactory(packageName);
    }
}
