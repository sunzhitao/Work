package com.szt.demo.ioc.dao.impl;

import com.szt.demo.ioc.dao.PersonDao;
import com.szt.ioc.anno.Component;

/**
 * @author: sunzhitao
 * @date: 2018/10/9 14:28
 * @description:
 */
@Component(id="personDaoImpl")
public class PersonDaoImpl implements PersonDao {
    @Override
    public void save() {
        System.out.println("保存person");
    }
}
