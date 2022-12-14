package com.aleksandrphilimonov.spring.mvc_hibernate_aop.dao;

import com.aleksandrphilimonov.spring.mvc_hibernate_aop.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired// в xml файле был создан бин этого класса, тут мы его внедряем в класс EmployeeDAOImpl
    private SessionFactory sessionFactory;

    @Override
    //@Transactional//будет автоматом открывать и закрывать транзакции - перенесли в сервис
    public List<Employee> getAllEmployees() {
        Session session = sessionFactory.getCurrentSession();

        Query<Employee> query = session.createQuery("from Employee", Employee.class);
        List<Employee> allEmployees = query.getResultList();
        // List<Employee> allEmployees = session.createQuery("from Employee", Employee.class).getResultList(); то же самое в одну строчку
        return allEmployees;
    }

    @Override
    public void saveEmployee(Employee employee) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(employee);
    }

    @Override
    public Employee getEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();

        Employee employee = session.get(Employee.class, id);
        return employee;
    }

    @Override
    public void deleteEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();
        /*Удаление в два запроса - дольше
        Employee employee = session.get(Employee.class, id);
        session.delete(employee);
         */
        //Удаление в один запрос
        Query<Employee> query = session.createQuery("delete from Employee where id =:employeeId");
        query.setParameter("employeeId", id);//произойдет замена названия параметра employeeId на значение
        // параметра, достигается с помощью метода setParameter()
        query.executeUpdate();
    }
}
