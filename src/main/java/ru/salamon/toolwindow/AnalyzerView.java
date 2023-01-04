package ru.salamon.toolwindow;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;

public class AnalyzerView implements Disposable {

    private Project myProject;

    public AnalyzerView(@NotNull Project project) {
        myProject = project;
    }

    public void initToolWindow(@NotNull ToolWindow toolWindow) {
    }

    @Override
    public void dispose() {

    }
}


