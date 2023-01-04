package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;
import kotlin.sequences.Sequence;
import org.jetbrains.teamcity.rest.TestRun;

import java.util.ArrayList;

class BuildNode extends CachingSimpleNode {

    private final String name;

    private final Sequence<TestRun> testRuns;

    protected BuildNode(SimpleNode aParent, Sequence<TestRun> testRuns, String name) {
        super(aParent);
        this.name = name;
        this.testRuns = testRuns;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected SimpleNode[] buildChildren() {
        var testRunNodes = new ArrayList<TestRunNode>();
        testRuns
                .iterator()
                .forEachRemaining(testRun -> testRunNodes.add(new TestRunNode(this, testRun)));

        return testRunNodes.toArray(new TestRunNode[0]);
    }

}