package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.SimpleNode;
import ru.salamon.model.ProjectModel;
import ru.salamon.model.TreeModel;

import java.util.stream.Collectors;

public class ProjectNode extends TreeNode {

    private final ProjectModel projectModel;

    public ProjectNode(SimpleNode aParent, ProjectModel projectModel) {
        super(aParent);
        this.projectModel = projectModel;
    }

    @Override
    public String getName() {
        return projectModel.getName() + " project";
    }

    @Override
    protected TreeNode[] buildChildren() {
        return projectModel.getProjects()
                .stream()
                .map(treeModel->treeModel.createNode(this))
                .collect(Collectors.toSet())
                .toArray(TreeNode[]::new);
    }

}
