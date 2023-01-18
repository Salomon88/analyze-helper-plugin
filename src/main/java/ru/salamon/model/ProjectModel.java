package ru.salamon.model;

import ru.salamon.model.tree.ProjectNode;
import ru.salamon.model.tree.TreeNode;

import java.util.Set;

public class ProjectModel implements TreeModel {

    private final String name;
    private final Set<TreeModel> projects;

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

    @Override
    public TreeNode createNode(TreeNode treeNode) {
        return new ProjectNode(treeNode, this);
    }

}
