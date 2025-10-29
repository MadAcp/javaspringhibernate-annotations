package org.javahibernate;

import org.javahibernate.config.AppConfig;
import org.javahibernate.model.*;
import org.javahibernate.service.*;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get all Services
        DepartmentService deptService = ctx.getBean(DepartmentService.class);
        ProjectService projectService = ctx.getBean(ProjectService.class);
        EmployeeService empService = ctx.getBean(EmployeeService.class);

        // ---- 1. Add Departments ----
        Department itDept = new Department("IT", 500000);
        Department hrDept = new Department("HR", 300000);
        deptService.save(itDept);
        deptService.save(hrDept);

        // ---- 2. Add Projects ----
        Project payrollProj = new Project("Payroll System", 200000);
        Project inventoryProj = new Project("Inventory System", 150000);
        projectService.save(payrollProj);
        projectService.save(inventoryProj);

        // ---- 3. Add Employees with relationships ----
        Address addr1 = new Address("101 Main St", "Mumbai", "MH", "400001");
        Employee emp1 = new Employee("Alice", 29, 70000, itDept, addr1);
        Employee emp2 = new Employee("Bob", 32, 65000, hrDept, new Address("102 Park Lane", "Pune", "MH", "411001"));
        empService.save(emp1);
        empService.save(emp2);

        // ---- 4. Assign Projects to Employees ----
        empService.assignToProjects(emp1.getId(), Arrays.asList(payrollProj, inventoryProj));
        empService.assignToProjects(emp2.getId(), Arrays.asList(inventoryProj));

        // ---- 5. Update Department Budget ----
        deptService.updateBudget(hrDept.getId(), 350000);

        // ---- 6. Update Employee details ----
        empService.updateEmployee(emp1.getId(), "IT", 75000.0, 30); // new salary/age

        // ---- 7. Fetch and display all Departments ----
        System.out.println("--- All Departments ---");
        deptService.getAll().forEach(System.out::println);

        // ---- 8. Fetch and display all Projects ----
        System.out.println("--- All Projects ---");
        projectService.getAll().forEach(System.out::println);

        // ---- 9. Fetch and display all Employees ----
        System.out.println("--- All Employees ---");
        empService.getAll().forEach(System.out::println);

        // ---- 10. Get Employees by Department ----
        System.out.println("--- Employees in IT Department ---");
        empService.getByDepartmentId(itDept.getId()).forEach(System.out::println);

        // ---- 11. Get Employees by Project ----
        System.out.println("--- Employees in Inventory System Project ---");
        empService.getByProjectId(inventoryProj.getId()).forEach(System.out::println);

        // ---- 12. Remove Employee ----
        empService.delete(emp2.getId());
        System.out.println("Bob deleted.");

        // ---- 13. Remove Project ----
        projectService.delete(payrollProj.getId());
        System.out.println("Payroll System project deleted.");

        // ---- 14. Remove Department ----
        deptService.delete(hrDept.getId());
        System.out.println("HR Department deleted.");

        // ---- 15. Show results after deletes ----
        System.out.println("--- Employees (final) ---");
        empService.getAll().forEach(System.out::println);

        System.out.println("--- Departments (final) ---");
        deptService.getAll().forEach(System.out::println);

        System.out.println("--- Projects (final) ---");
        projectService.getAll().forEach(System.out::println);

        ctx.close();
    }
}
