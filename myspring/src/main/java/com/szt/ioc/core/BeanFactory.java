package com.szt.ioc.core;

import com.szt.ioc.anno.Autowire;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {
 
    private static  HashMap<String, Object> beanPool;
    private static  HashMap<String, Object> aspectBeanPool;
    /**
     *   包含扫描到的bean的包名
     */
    private static  HashMap<String, String> components;
    private static  HashMap<String, String> aspects;
    
    public BeanFactory(String packageName) {
        beanPool = new HashMap<>();
        //扫描包下面的bean，放入bean工厂
        scanComponents(packageName);
        //扫描包下面的aspect
        scanAspects(packageName);
    }

    /**
     * 获取指定包名下所有的对象（有components注解的类）
     * @param packageName
     */
    private void scanComponents(String packageName) {
        components = ComponentScanner.getComponentClassName(packageName);
    }
    private void scanAspects(String packageName) {
         aspects = ComponentScanner.getAspectClassName(packageName);
    }



    public static Object getBean(String id) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, 
            NoSuchMethodException, SecurityException, 
            IllegalArgumentException, InvocationTargetException {

        if (beanPool.containsKey(id)) {
            BeanDefinition beanDefinition = (BeanDefinition) beanPool.get(id);
            return beanDefinition.getObject();
        }else if(components.containsKey(id)) {
            Object bean = Class.forName(components.get(id))
                    .newInstance();
            bean = assemblyMember(bean);
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setObject(bean);

            beanPool.put(id, beanDefinition);
            return getBean(id);
        }else {
            throw new ClassNotFoundException();
        }
    }

    public static Object getAspectBean(String id) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException {

        if (aspectBeanPool.containsKey(id)) {
            BeanDefinition beanDefinition = (BeanDefinition) aspectBeanPool.get(id);
            return beanDefinition.getObject();
        }else if(aspects.containsKey(id)) {
            Object bean = Class.forName(aspects.get(id))
                    .newInstance();
            bean = assemblyMember(bean);
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setObject(bean);
            aspectBeanPool.put(id, beanDefinition);
            return getAspectBean(id);
        }else {
            throw new ClassNotFoundException();
        }
    }
    
    private static Object assemblyMember(Object obj) throws
            ClassNotFoundException, InstantiationException, 
            IllegalAccessException, NoSuchMethodException, 
            SecurityException, IllegalArgumentException, 
            InvocationTargetException {
        
        Class cl = obj.getClass();
        
        for (Field f : cl.getDeclaredFields()) {
            Autowire at = f.getAnnotation(Autowire.class);
            if (at != null) {
                Method setMethod = cl.getMethod("set"
                        + captureName(f.getName()), f.getType());

                setMethod.invoke(obj, getBean(at.id()));
            }
        }
        return obj;
    }
    
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    public Map<String,Object> getBeanMap(){
        return beanPool;
    }

    public Map<String,Object> getAspectBeanMap(){
        return aspectBeanPool;
    }

}