package ru.salamon.model.builder;

import com.google.common.collect.Streams;
import kotlin.sequences.SequencesKt;
import org.jetbrains.teamcity.rest.BuildConfiguration;
import org.jetbrains.teamcity.rest.Project;
import org.jetbrains.teamcity.rest.TestStatus;
import ru.salamon.model.*;
import ru.salamon.resources.ResourceFetcher;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProjectBuilder {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("teamcity");

    public static TreeModel project() {
        return project(ResourceFetcher.fetchProject(bundle.getString("buildId")));
    }

    public static TreeModel project(Project project) {
        var models = Streams.concat(
                project.getChildProjects().stream().map(ProjectBuilder::project),
                project.getBuildConfigurations().stream().map(ProjectBuilder::buildConfig)
        ).collect(Collectors.toSet());
        return new ProjectModel(ResourceFetcher.fetchProjectName(project.getId().getStringId()), models);
    }

    public static TreeModel buildConfig(BuildConfiguration buildConfiguration) {
        var builds = ResourceFetcher.fetchBuildsByConfId(buildConfiguration.getId().getStringId())
                .stream()
                .map(build -> {
                    var testRuns = SequencesKt
                            .toList(build.testRuns(TestStatus.FAILED))
                            .stream()
                            .map(testRun -> {
                                var metadata = new HashSet<String>();
                                if (testRun.getMetadataValues() != null || !testRun.getMetadataValues().isEmpty()) {
                                    metadata.addAll(testRun.getMetadataValues());
                                }
                                return new TestRunModel(testRun.getName(), metadata);
                            })
                            .collect(Collectors.toSet());

                    return new BuildModel(build.getName() + " - " + build.getStartDateTime(), testRuns);
                })
                .collect(Collectors.toSet());

        return new BuildConfigurationModel(buildConfiguration.getName(), builds);
    }


//    void buildTestRuns(BuildModel build) {
//        var testRunNodes = new ArrayList<TestRunNode>();
//        testRuns
//                .iterator()
//                .forEachRemaining(testRun -> testRunNodes.add(new TestRunNode(this, testRun)));
//
//        return testRunNodes.toArray(new TestRunNode[0]);
//    }
//
//    Set<String> buildChildren(TestRunModel testRun) {
//
//        if (testRun.getMetadataValues() == null || testRun.getMetadataValues().isEmpty()) {
//            return new MetadataNodeItem[]{new MetadataNodeItem(this, "No test metadata was found")};
//        }
//
//        return testRun.getMetadataValues().stream()
//                .map(metadata -> new MetadataNodeItem(this, metadata))
//                .collect(Collectors.toList())
//                .toArray(new MetadataNodeItem[1]);
//    }

}
