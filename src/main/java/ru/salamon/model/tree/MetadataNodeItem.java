package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;

public class MetadataNodeItem extends CachingSimpleNode {

    private final String name;

    protected MetadataNodeItem(SimpleNode aParent, String name) {
        super(aParent);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return new SimpleNode[]{};
    }

    @Override
    public boolean isAlwaysLeaf() {
        return true;
    }


}
