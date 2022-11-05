package com.aleksandrphilimonov.spring.mvc_hibernate_aop.dao;

import com.aleksandrphilimonov.spring.mvc_hibernate_aop.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    //!Transactional - ответственность за открытие/закрытие транзакции
    @Transactional
    public List<Employee> getAllEmployees() {

        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Employee ", Employee.class)
                .getResultList();
    }
}
