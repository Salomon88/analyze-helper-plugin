package ru.salamon.model.tree;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;

public abstract class TreeNode extends CachingSimpleNode {

    protected TreeNode(SimpleNode aParent) {
        super(aParent);
    }
}
