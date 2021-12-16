package ru.salamon.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.teamcity.rest.TestStatus;
import ru.salamon.resources.ResourceFetcher;

public class FetchBuildListAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var builds = ResourceFetcher.fetchBuildList("ijplatform_master_PhpStorm_IntegrationInspectionTests");
        builds.forEach(build -> {
            System.out.println(build.getName() + "-" + build.getBuildNumber());
            build.testRuns(TestStatus.FAILED).iterator()
                    .forEachRemaining(testRun -> System.out.println(testRun.getName()));
        });
    }
}
