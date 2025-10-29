package org.javahibernate.dao;

import org.hibernate.Session;
import org.javahibernate.model.Employee;
import org.javahibernate.model.Project;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProjectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void save(Project project) {
        sessionFactory.getCurrentSession().save(project);
    }

    @Transactional(readOnly = true)
    public Project getById(int id) {
        return sessionFactory.getCurrentSession().get(Project.class, id);
    }

    @Transactional(readOnly = true)
    public List<Project> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Project", Project.class).list();
    }

    @Transactional
    public void updateBudget(int id, double newBudget) {
        Project project = getById(id);
        if (project != null) {
            project.setBudget(newBudget);
            sessionFactory.getCurrentSession().update(project);
        }
    }

    @Transactional
    public void detachEmployeesFromProject(Project project) {
        Session session = sessionFactory.getCurrentSession();
        List<Employee> employees = session
                .createQuery("select e from Employee e join e.projects p where p.id = :pid", Employee.class)
                .setParameter("pid", project.getId())
                .list();

        for (Employee e : employees) {
            e.getProjects().removeIf(p -> p.getId() == project.getId());
            session.update(e); // Persists the change
        }
    }

    @Transactional
    public void delete(int id) {
        Project project = getById(id);
        if (project != null) {
            detachEmployeesFromProject(project);
            sessionFactory.getCurrentSession().delete(project);
        }
    }
}
