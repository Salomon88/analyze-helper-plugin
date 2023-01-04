package ru.salamon.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import ru.salamon.toolwindow.AnalyzerPanel;

public class AddMetadataContentAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var toolWindow = ToolWindowManager.getInstance(e.getProject()).getToolWindow("Analyzer View");

        if (toolWindow != null && toolWindow.getContentManager().getContents().length == 0) {
            toolWindow
                    .getContentManager()
                    .addContent(ContentFactory.SERVICE.getInstance().createContent(new AnalyzerPanel(e.getProject()), "Analyzer Content", true));
            toolWindow.activate(null);
        }
    }
}
