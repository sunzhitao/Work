package com.szt.test;

import com.szt.App;
import com.szt.Container;
import com.szt.demo.ioc.service.PersonService;
import com.szt.ioc.core.BeanFactory;

public class PersonTest {

	public static void main(String[] args) throws Exception{
		Container.run(App.class);
		PersonService personService = (PersonService)BeanFactory.getBean("personService");
		personService.save();
	}

}








