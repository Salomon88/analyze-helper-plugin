package ru.salamon.model.tree;

import ru.salamon.model.BuildConfigurationModel;

class BuildConfigurationNode extends TreeNode {

    private final BuildConfigurationModel buildConfigurationModel;

    public BuildConfigurationNode(ProjectNode tcRootNode, BuildConfigurationModel buildConfigurationModel) {
        super(tcRootNode);
        this.buildConfigurationModel = buildConfigurationModel;
    }

    @Override
    protected BuildNode[] buildChildren() {
        return buildConfigurationModel
                .getBuildModels()
                .stream()
                .map(build -> new BuildNode(this, build))
                .toArray(BuildNode[]::new);
    }

    @Override
    public String getName() {
        return buildConfigurationModel.getName();
    }
}
