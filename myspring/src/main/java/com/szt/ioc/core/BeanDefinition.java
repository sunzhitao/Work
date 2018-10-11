package com.szt.ioc.core;

import lombok.Data;

@Data
public class BeanDefinition {
    private Object proxyObject;//代理对象
    private Object object;//对象
}
