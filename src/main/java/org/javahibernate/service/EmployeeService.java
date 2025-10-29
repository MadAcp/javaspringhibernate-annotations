package org.javahibernate.service;

import org.javahibernate.dao.EmployeeDAO;
import org.javahibernate.model.Department;
import org.javahibernate.model.Employee;
import org.javahibernate.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public void save(Employee employee) { employeeDAO.save(employee); }
    public Employee getById(int id) { return employeeDAO.getById(id); }
    public List<Employee> getAll() { return employeeDAO.getAll(); }
    public void updateEmployee(int id, String newDept, Double newSalary, Integer newAge) {
        employeeDAO.updateEmployee(id, newDept, newSalary, newAge);
    }
    public void delete(int id) { employeeDAO.delete(id); }
    public void assignToDepartment(int empId, Department dept) { employeeDAO.assignToDepartment(empId, dept); }
    public void assignToProjects(int empId, List<Project> projects) { employeeDAO.assignToProjects(empId, projects); }
    public List<Employee> getByDepartmentId(int deptId) { return employeeDAO.getByDepartmentId(deptId); }
    public List<Employee> getByProjectId(int projectId) { return employeeDAO.getByProjectId(projectId); }
}
