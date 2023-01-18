package ru.salamon.model;

import ru.salamon.model.tree.BuildConfigurationNode;
import ru.salamon.model.tree.ProjectNode;
import ru.salamon.model.tree.TreeNode;

import java.util.Set;

public class BuildConfigurationModel implements TreeModel {

    private final String name;
    private final Set<BuildModel> buildModels;

    public BuildConfigurationModel(String name, Set<BuildModel> buildModels) {
        this.name = name;
        this.buildModels = buildModels;
    }

    public String getName() {
        return name;
    }

    public Set<BuildModel> getBuildModels() {
        return buildModels;
    }

    @Override
    public TreeNode createNode(TreeNode treeNode) {
        return new BuildConfigurationNode((ProjectNode) treeNode, this);
    }

}
