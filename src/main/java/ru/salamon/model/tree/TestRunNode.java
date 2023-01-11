package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;
import org.jetbrains.teamcity.rest.TestRun;
import ru.salamon.model.TestRunModel;

import java.util.stream.Collectors;

class TestRunNode extends CachingSimpleNode {

    private final TestRunModel testRunModel;

    protected TestRunNode(SimpleNode aParent, TestRunModel testRunModel) {
        super(aParent);
        this.testRunModel = testRunModel;
    }

    @Override
    public String getName() {
        return testRunModel.getName();
    }


    //Example
    //metaData.add("+ /usesnpe/UsesRedundant.php:67 WEAK WARNING (int) 'Type cast is redundant'");
    @Override
    protected MetadataNodeItem[] buildChildren() {

        if (testRunModel.getMetadata().isEmpty()) {
            return new MetadataNodeItem[]{new MetadataNodeItem(this, "No test metadata was found")};
        }

        return testRunModel
                .getMetadata()
                .stream()
                .map(metadata -> new MetadataNodeItem(this, metadata))
                .collect(Collectors.toList())
                .toArray(new MetadataNodeItem[1]);
    }

}
