package ru.salamon.model.tree;

import org.jetbrains.teamcity.rest.TestStatus;
import ru.salamon.resources.ResourceFetcher;

class BuildConfigurationNode extends TreeNode {
    private final String name;
    private final String configurationId;


    public BuildConfigurationNode(ProjectNode tcRootNode, String name, String configurationId) {
        super(tcRootNode);
        this.name = name;
        this.configurationId = configurationId;
    }

    @Override
    protected BuildNode[] buildChildren() {
        var buildList = ResourceFetcher.fetchBuildsByConfId(this.configurationId);

        return buildList
                .stream()
                .map(build -> new BuildNode(this, build.testRuns(TestStatus.FAILED), build.getName() + " - " + build.getStartDateTime()))
                .toArray(BuildNode[]::new);
    }

    @Override
    public String getName() {
        return name;
    }
}
