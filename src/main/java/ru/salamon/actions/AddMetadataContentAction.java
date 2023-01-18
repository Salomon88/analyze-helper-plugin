package ru.salamon.actions;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeBundle;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import ru.salamon.toolwindow.AnalyzerPanel;

public class AddMetadataContentAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var toolWindow = ToolWindowManager.getInstance(e.getProject()).getToolWindow("Analyzer View");

        if (toolWindow != null && toolWindow.getContentManager().getContents().length == 0) {
            var toolWindowEx = (ToolWindowEx) toolWindow;
            toolWindowEx
                    .getContentManager()
                    .addContent(ContentFactory.SERVICE.getInstance().createContent(new AnalyzerPanel(e.getProject(), toolWindow), "Analyzer Content", true));
//            toolWindowEx.setTabActions(
//                    new DumbAwareAction("", "", AllIcons.General.Add) {
//                        @Override
//                        public void actionPerformed(@NotNull AnActionEvent e) {
//                            toolWindowEx.getContentManager().addContent(ContentFactory.SERVICE.getInstance().createContent(new AnalyzerPanel(e.getProject(), toolWindow), "Analyzer Content", true));
//                        }
//                    }
//            );
            toolWindow.activate(null);
        }
    }
}
