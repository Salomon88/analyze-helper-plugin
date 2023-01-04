package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;
import org.jetbrains.teamcity.rest.TestRun;

import java.util.stream.Collectors;

class TestRunNode extends CachingSimpleNode {

    private final TestRun testRun;

    protected TestRunNode(SimpleNode aParent, TestRun testRun) {
        super(aParent);
        this.testRun = testRun;
    }

    @Override
    public String getName() {
        return testRun.getName();
    }


    //Example
    //metaData.add("+ /usesnpe/UsesRedundant.php:67 WEAK WARNING (int) 'Type cast is redundant'");
    @Override
    protected MetadataNodeItem[] buildChildren() {

        if (testRun.getMetadataValues() == null || testRun.getMetadataValues().isEmpty()) {
            return new MetadataNodeItem[]{new MetadataNodeItem(this, "No test metadata was found")};
        }

        return testRun.getMetadataValues().stream()
                .map(metadata -> new MetadataNodeItem(this, metadata))
                .collect(Collectors.toList())
                .toArray(new MetadataNodeItem[1]);
    }

}
