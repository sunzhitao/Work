package com.szt;


import com.szt.ioc.IocContainer;

public class Container {

    public static void run(Class<?> clazz) throws Exception {
        //初始化ioc容器
        IocContainer.run(clazz);
    }
}