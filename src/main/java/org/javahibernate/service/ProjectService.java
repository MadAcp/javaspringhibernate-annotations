package org.javahibernate.service;

import org.javahibernate.dao.ProjectDAO;
import org.javahibernate.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    public void save(Project project) { projectDAO.save(project); }
    public Project getById(int id) { return projectDAO.getById(id); }
    public List<Project> getAll() { return projectDAO.getAll(); }
    public void updateBudget(int id, double newBudget) { projectDAO.updateBudget(id, newBudget); }
    public void delete(int id) { projectDAO.delete(id); }
}
