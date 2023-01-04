package ru.salamon.model.tree;

import com.google.common.collect.Streams;
import com.intellij.ui.treeStructure.SimpleNode;
import ru.salamon.resources.ResourceFetcher;

import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProjectNode extends TreeNode {

    private final ResourceBundle bundle = ResourceBundle.getBundle("teamcity");
    private final String projectId;

    public ProjectNode(SimpleNode aParent, String projectId) {
        super(aParent);
        this.projectId = projectId;
    }


    @Override
    public String getName() {
        return ResourceFetcher.fetchProjectName(this.projectId);
    }

    @Override
    protected TreeNode[] buildChildren() {
        var project = ResourceFetcher.fetchProject(this.projectId);
        return Streams.concat(
                        project.getChildProjects().stream().map(NodeFactory::getNode),
                        project.getBuildConfigurations().stream().map(NodeFactory::getNode)
                ).collect(Collectors.toList())
                .toArray(new TreeNode[1]);

    }
}
