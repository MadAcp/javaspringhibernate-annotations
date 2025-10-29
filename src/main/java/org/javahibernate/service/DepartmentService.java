package org.javahibernate.service;

import org.javahibernate.dao.DepartmentDAO;
import org.javahibernate.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDAO departmentDAO;

    public void save(Department department) { departmentDAO.save(department); }
    public Department getById(int id) { return departmentDAO.getById(id); }
    public List<Department> getAll() { return departmentDAO.getAll(); }
    public void updateBudget(int id, double newBudget) { departmentDAO.updateBudget(id, newBudget); }
    public void delete(int id) { departmentDAO.delete(id); }
}
