package com.szt.ioc.core;

import com.szt.aop.anno.MyAspect;
import com.szt.ioc.anno.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
public class ComponentScanner {
 
    public static HashMap<String, String> getComponentClassName(
            String packageName) {
        List<String> classes = getClassName(packageName);
        //所有包含components的类
        HashMap<String, String> components = new HashMap<>(8);

        try {
            for (String cl : classes) {
                Component comp = Class.forName(cl).getAnnotation(Component.class);

                if (comp != null) {
                    components.put(comp.id(), cl);
                }
            }
        
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return components;
    }

    public static HashMap<String, String> getAspectClassName(
            String packageName) {
        List<String> classes = getClassName(packageName);
        //所有包含aspect的类
        HashMap<String, String> aspects = new HashMap<>(8);

        try {
            for (String cl : classes) {
                MyAspect aspect = Class.forName(cl).getAnnotation(MyAspect.class);
                if (aspect != null) {
                    aspects.put(aspect.id(), cl);
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return aspects;
    }
    
    public static List<String> getClassName(String packageName) {
        String filePath = ClassLoader.getSystemResource("").getPath() 
                + packageName.replace(".", "\\");  
        List<String> fileNames = getClassName(filePath, null);
        return fileNames;
    }
    
    private static List<String> getClassName(String filePath
            , List<String> className) {  
        List<String> myClassName = new ArrayList<String>();  
        File file = new File(filePath);  
        File[] childFiles = file.listFiles();  
        for (File childFile : childFiles) {  
            if (childFile.isDirectory()) {  
                myClassName.addAll(getClassName(childFile.getPath()
                        , myClassName));  
            } else {  
                String childFilePath = childFile.getPath();  
                childFilePath = childFilePath.substring(childFilePath
                        .indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));  
                childFilePath = childFilePath.replace("\\", ".");  
                myClassName.add(childFilePath);  
            }  
        }  
  
        return myClassName;  
    }
    
    public static void main(String[] args) {
        getComponentClassName("com.szt.demo.ioc.service.impl");
    }

}