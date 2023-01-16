package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import ru.salamon.model.ProjectModel;

import java.util.Set;

public class RootNode extends CachingSimpleNode {

    private final Set<ProjectModel> projects;

    public RootNode(Set<ProjectModel> projects) {
        super(null);
        this.projects = projects;
    }

    @Override
    public String getName() {
        return "Projects tree";
    }

    @Override
    protected ProjectNode[] buildChildren() {
        return projects
                .stream()
                .map(projectModel-> new ProjectNode(this, projectModel))
                .toArray(ProjectNode[]::new);
    }

}
