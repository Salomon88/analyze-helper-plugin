package ru.salamon.model;

import java.util.Set;

public class ProjectModel extends TreeModel {

    private String name;
    private Set<TreeModel> projects;

    public ProjectModel(String name, Set<TreeModel> projects) {
        this.name = name;
        this.projects = projects;
    }

    public String getName() {
        return name;
    }

    public Set<TreeModel> getProjects() {
        return Set.copyOf(projects);
    }
}
