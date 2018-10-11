package com.szt.demo.ioc.service.impl;
 
import com.szt.demo.ioc.dao.PersonDao;
import com.szt.demo.ioc.service.PersonService;
import com.szt.ioc.anno.Autowire;
import com.szt.ioc.anno.Component;

@Component(id="personService")
public class PersonServiceImpl implements PersonService {

	@Autowire(id="personDaoImpl")
	private PersonDao pdo;
 
	public PersonDao getPdo() {
		return pdo;
	}
 
	public void setPdo(PersonDao pdo) {
		this.pdo = pdo;
	}
 
	@Override
	public void save() {
		pdo.save();
	}
	
}
