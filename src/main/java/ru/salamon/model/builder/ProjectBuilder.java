package ru.salamon.model.builder;

import com.google.common.collect.Streams;
import com.intellij.openapi.components.Service;
import kotlin.sequences.SequencesKt;
import org.jetbrains.teamcity.rest.*;
import ru.salamon.model.*;
import ru.salamon.resources.ResourceFetcher;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public final class ProjectBuilder {

    public TreeModel project(String projectId) {
        return project(ResourceFetcher.fetchProject(projectId));
    }

    public TreeModel project(Project project) {
        var models = Streams.concat(
                project.getChildProjects().stream().map(this::project),
                project.getBuildConfigurations().stream().map(this::buildConfig)
        ).collect(Collectors.toSet());
        return new ProjectModel(ResourceFetcher.fetchProjectName(project.getId().getStringId()), models);
    }

    public TreeModel buildConfig(BuildConfiguration buildConfiguration) {
        var builds = ResourceFetcher.fetchBuildsByConfId(buildConfiguration.getId().getStringId())
                .stream()
                .map(this::build)
                .collect(Collectors.toSet());
        return new BuildConfigurationModel(buildConfiguration.getName(), builds);
    }


    public BuildModel build(Build build) {
        var testRuns = SequencesKt
                .toList(build.testRuns(TestStatus.FAILED))
                .stream()
                .map(this::testRun)
                .collect(Collectors.toSet());
        return new BuildModel(build.getName() + " - " + build.getStartDateTime(), testRuns);
    }

    public  TestRunModel testRun(TestRun testRun) {
        var metadata = new HashSet<String>();
        if (testRun.getMetadataValues() != null || !testRun.getMetadataValues().isEmpty()) {
            metadata.addAll(testRun.getMetadataValues());
        }
        return new TestRunModel(testRun.getName(), metadata);
    }

}
