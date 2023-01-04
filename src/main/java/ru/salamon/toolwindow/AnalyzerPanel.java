package ru.salamon.toolwindow;

import com.intellij.ide.CommonActionsManager;
import com.intellij.ide.DefaultTreeExpander;
import com.intellij.ide.util.PsiNavigationSupport;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.ui.AutoScrollToSourceHandler;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBLoadingPanel;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import com.intellij.util.EditSourceOnDoubleClickHandler;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.salamon.FailureInspectionFilter;
import ru.salamon.model.tree.ProjectNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.util.ResourceBundle;

import static com.intellij.util.PathUtil.getLocalPath;

public class AnalyzerPanel extends SimpleToolWindowPanel implements DataProvider, Disposable {

    private final Project myProject;
    private final SimpleTree myTree;
    private static final ResourceBundle bundle = ResourceBundle.getBundle("teamcity");

    public AnalyzerPanel(@NotNull Project project) {
        super(false, true);
        myProject = project;
        StructureTreeModel<SimpleTreeStructure> treeModel = new StructureTreeModel<>(
                new SimpleTreeStructure() {
                    @NotNull
                    @Override
                    public ProjectNode getRootElement() {
                        return new ProjectNode(null, bundle.getString("buildId"));
                    }
                }, null, myProject
        );

        myTree = new SimpleTree(new AsyncTreeModel(treeModel, myProject));
        var myAutoScrollToSourceHandler = new MyAutoScrollToSourceHandler();
        myAutoScrollToSourceHandler.install(myTree);
        EditSourceOnDoubleClickHandler.install(myTree);
        var toolbarGroup = new DefaultActionGroup();
        var treeExpander = new DefaultTreeExpander(myTree);
        toolbarGroup.add(CommonActionsManager.getInstance().createExpandAllAction(treeExpander, this));
        toolbarGroup.add(CommonActionsManager.getInstance().createCollapseAllAction(treeExpander, this));
        //TODO create settings action to configure period of fetching data
        setToolbar(ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, toolbarGroup, false).getComponent());
        setContent(createCenterComponent());
    }

    protected JComponent createCenterComponent() {
        Splitter splitter = new OnePixelSplitter(false);
        JBLoadingPanel loadingPanel = new JBLoadingPanel(new BorderLayout(), this, 1000);
        loadingPanel.add(ScrollPaneFactory.createScrollPane(myTree), BorderLayout.CENTER);
        splitter.setFirstComponent(loadingPanel);
        return splitter;
    }


    //TODO add all these configuration and add ability to add custom
    //ijplatform_master_PhpStormIntegrationInspectionsTestsBucketsDefault
    // ijplatform_master_PhpStormIntegrationInspectionsTestsBucketsDefaultPhp8
   //ijplatform_master_PhpStormIntegrationInspectionsTestsBucketsDisabled
   //ijplatform_master_PhpStormIntegrationInspectionsTestsBucketsDisabledPhp8

    //+ /usesnpe/UsesRedundant.php:67 WEAK WARNING (int) 'Type cast is redundant'
    // + wp-includes/sodium_compat/src/Core32/HSalsa20.php

    /**
     * Method which navigate to concrete position in file using the following pattern: + /wp-admin/edit-link-form.php:161 WEAK WARNING (int) 'Type cast is redundant'
     *
     * @param dataId the data identifier for which the value is requested.
     * @return navigable object
     */
    @Override
    public @Nullable Object getData(@NotNull @NonNls String dataId) {
        if (CommonDataKeys.NAVIGATABLE.is(dataId)) {
            TreePath path = myTree.getSelectionPath();
            if (path == null) return null;
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            Object userObject = node.getUserObject();
            if (!(userObject instanceof NodeDescriptor)) return null;

            var matcher = FailureInspectionFilter.PATTERN.matcher(node.getUserObject().toString());
            if (matcher.find()) {
                var possibleFilePath = getLocalPath(matcher.group(1));
                var vf = LocalFileSystem.getInstance().findFileByPath(myProject.getBasePath() + "/" + possibleFilePath);
                if (vf != null) {
                    return PsiNavigationSupport.getInstance().createNavigatable(myProject, vf, Integer.parseInt(matcher.group(2)));
                }
//                else {
//                    NotificationGroupManager.getInstance().getNotificationGroup("PStorm test analyzer")
//                            .createNotification("File path " + possibleFilePath + " doesn't exist", NotificationType.WARNING)
//                            .notify(myProject);
//                }
            } else {
                return null;
            }
        }
        return super.getData(dataId);
    }

    @Override
    public void dispose() {}

    private static final class MyAutoScrollToSourceHandler extends AutoScrollToSourceHandler {
        MyAutoScrollToSourceHandler() {
        }

        @Override
        protected boolean isAutoScrollMode() {
            return true;
        }

        @Override
        protected void setAutoScrollMode(boolean state) {

        }
    }

}