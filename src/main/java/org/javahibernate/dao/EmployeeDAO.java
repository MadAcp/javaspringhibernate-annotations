package org.javahibernate.dao;

import org.javahibernate.model.Employee;
import org.javahibernate.model.Department;
import org.javahibernate.model.Project;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void save(Employee employee) {
        sessionFactory.getCurrentSession().save(employee);
    }

    @Transactional(readOnly = true)
    public Employee getById(int id) {
        return sessionFactory.getCurrentSession().get(Employee.class, id);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employee", Employee.class).list();
    }

    @Transactional
    public void updateEmployee(int id, String newDeptName, Double newSalary, Integer newAge) {
        Employee e = getById(id);
        if (e != null) {
            if (newDeptName != null) {
                // Lookup department by name (or id—whichever you want)
                Department dept = sessionFactory.getCurrentSession()
                        .createQuery("from Department where name = :dname", Department.class)
                        .setParameter("dname", newDeptName)
                        .uniqueResult();
                if (dept != null) {
                    e.setDepartment(dept);
                } else {
                    // Optionally: Create and SAVE new department first if you want
                    Department newDept = new Department(newDeptName, 0);
                    sessionFactory.getCurrentSession().save(newDept);
                    e.setDepartment(newDept);
                }
            }
            if (newSalary != null) e.setSalary(newSalary);
            if (newAge != null) e.setAge(newAge);
            sessionFactory.getCurrentSession().update(e);
        }
    }


    @Transactional
    public void delete(int id) {
        Employee e = getById(id);
        if (e != null) sessionFactory.getCurrentSession().delete(e);
    }

    @Transactional
    public void assignToDepartment(int empId, Department dept) {
        Employee e = getById(empId);
        if (e != null && dept != null) {
            e.setDepartment(dept);
            sessionFactory.getCurrentSession().update(e);
        }
    }

    @Transactional
    public void assignToProjects(int empId, List<Project> projects) {
        Employee e = getById(empId);
        if (e != null && projects != null) {
            e.setProjects(projects);
            sessionFactory.getCurrentSession().update(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Employee> getByDepartmentId(int deptId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employee where department.id = :deptId", Employee.class)
                .setParameter("deptId", deptId)
                .list();
    }

    @Transactional(readOnly = true)
    public List<Employee> getByProjectId(int projectId) {
        return sessionFactory.getCurrentSession()
                .createQuery("select e from Employee e join e.projects p where p.id = :projectId", Employee.class)
                .setParameter("projectId", projectId)
                .list();
    }
}
