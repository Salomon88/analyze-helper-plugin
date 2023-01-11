package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;
import ru.salamon.model.BuildModel;

import java.util.stream.Collectors;

class BuildNode extends CachingSimpleNode {

    private final BuildModel buildModel;

    protected BuildNode(SimpleNode aParent, BuildModel buildModel) {
        super(aParent);
        this.buildModel = buildModel;
    }

    @Override
    public String getName() {
        return buildModel.getBuildName();
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return buildModel
                .getTestRuns()
                .stream()
                .map(testRun -> new TestRunNode(this, testRun))
                .collect(Collectors.toSet())
                .toArray(new TestRunNode[0]);
        }

}