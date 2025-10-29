package org.javahibernate.dao;

import org.javahibernate.model.Department;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DepartmentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void save(Department department) {
        sessionFactory.getCurrentSession().save(department);
    }

    @Transactional(readOnly = true)
    public Department getById(int id) {
        return sessionFactory.getCurrentSession().get(Department.class, id);
    }

    @Transactional(readOnly = true)
    public List<Department> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Department", Department.class).list();
    }

    @Transactional
    public void updateBudget(int id, double newBudget) {
        Department dept = getById(id);
        if (dept != null) {
            dept.setBudget(newBudget);
            sessionFactory.getCurrentSession().update(dept);
        }
    }

    @Transactional
    public void delete(int id) {
        Department dept = getById(id);
        if (dept != null) sessionFactory.getCurrentSession().delete(dept);
    }
}
