package com.szt;

import com.szt.demo.ioc.service.PersonService;
import com.szt.ioc.core.BeanFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws Exception{
        Container.run(App.class);
        PersonService personService = (PersonService) BeanFactory.getBean("personService");
        personService.save();
        System.in.read(); // 按任意键退出
    }
}
