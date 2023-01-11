package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.SimpleNode;
import ru.salamon.model.BuildConfigurationModel;
import ru.salamon.model.ProjectModel;
import java.util.stream.Collectors;

public class ProjectNode extends TreeNode {

    private final ProjectModel projectModel;

    public ProjectNode(SimpleNode aParent, ProjectModel projectModel) {
        super(aParent);
        this.projectModel = projectModel;
    }

    @Override
    public String getName() {
        return projectModel.getName();
    }

    @Override
    protected TreeNode[] buildChildren() {
        return projectModel.getProjects()
                .stream()
                .map(treeModel -> {
                    if (treeModel instanceof ProjectModel) {
                        return new ProjectNode(this, (ProjectModel) treeModel);
                    } else {
                        return new BuildConfigurationNode(this, (BuildConfigurationModel) treeModel);
                    }
                })
                .collect(Collectors.toSet())
                .toArray(new TreeNode[1]);
    }

}
