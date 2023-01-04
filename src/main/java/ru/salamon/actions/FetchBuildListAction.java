package ru.salamon.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.teamcity.rest.TestStatus;
import ru.salamon.resources.ResourceFetcher;

import java.util.ResourceBundle;

public class FetchBuildListAction extends AnAction {

  private final ResourceBundle bundle = ResourceBundle.getBundle("teamcity");

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
//        ResourceFetcher.fetchBuildsByConfId(bundle.getString("buildId"))
//                .forEach(build -> {
//                    System.out.println(build.getName() + "-" + build.getBuildNumber());
//                    build.testRuns(TestStatus.FAILED).iterator()
//                            .forEachRemaining(testRun -> System.out.println(testRun.getName()));
//                });
    }
}
