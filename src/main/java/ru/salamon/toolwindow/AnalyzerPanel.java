package ru.salamon.toolwindow;

import com.intellij.ide.CommonActionsManager;
import com.intellij.ide.DefaultTreeExpander;
import com.intellij.ide.util.PsiNavigationSupport;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.VfsImplUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.PsiFileEx;
import com.intellij.psi.impl.file.PsiFileImplUtil;
import com.intellij.psi.impl.source.PsiFileImpl;
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
import ru.salamon.configuration.AppSettingsState;
import ru.salamon.model.ProjectModel;
import ru.salamon.model.builder.ProjectBuilder;
import ru.salamon.model.tree.RootNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.util.stream.Collectors;

import static com.intellij.util.PathUtil.getLocalPath;

public class AnalyzerPanel extends SimpleToolWindowPanel implements DataProvider, Disposable {

    private final Project myProject;
    private final ToolWindow toolWindow;
    private final SimpleTree myTree;

    public AnalyzerPanel(@NotNull Project project, ToolWindow toolWindow) {
        super(false, true);
        myProject = project;
        this.toolWindow = toolWindow;
        StructureTreeModel<SimpleTreeStructure> treeModel = new StructureTreeModel<>(
                new SimpleTreeStructure() {
                    @NotNull
                    @Override
                    public RootNode getRootElement() {
                        var projectBuilder = myProject.getService(ProjectBuilder.class);
                        return new RootNode(AppSettingsState
                                .getInstance()
                                .getProjectIds()
                                .stream()
                                .map(projectBuilder::project)
                                .map(treeModel -> (ProjectModel) treeModel)
                                .collect(Collectors.toSet())
                        );
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
                Document document;
                if (vf != null &&  (document = FileDocumentManager.getInstance().getDocument(vf)) !=null) {
                    var offset = document
                            .getLineStartOffset(Integer.parseInt(matcher.group(2))-1);
                    return PsiNavigationSupport.getInstance().createNavigatable(myProject, vf, offset);
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
    public void dispose() {
        toolWindow.hide();
    }

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