package ru.salamon.resources;

import it.unimi.dsi.fastutil.Pair;
import org.jetbrains.teamcity.rest.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ResourceFetcher {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("teamcity");
    private static final TeamCityInstance client = TeamCityInstanceFactory.guestAuth(bundle.getString("url"));

    public static List<Pair<String, BuildConfigurationId>> fetchBuildsByProject(String projectId) {
        var buildConfIds = new ArrayList<Pair<String, BuildConfigurationId>>();
        client
                .project(new ProjectId(projectId))
                .getBuildConfigurations()
                .forEach(buildConfiguration -> buildConfIds.add(Pair.of(buildConfiguration.getName(), buildConfiguration.getId())));
        return buildConfIds;
    }

    public static Project fetchProject(String projectId) {
        return client.project(new ProjectId(projectId));
    }

    public static List<Build> fetchBuildsByConfId(String buildConfigurationId) {
        var buildList = new ArrayList<Build>();
        client
                .builds()
                .fromConfiguration(new BuildConfigurationId(buildConfigurationId))
                .withStatus(BuildStatus.FAILURE)
                .all()
                .iterator()
                .forEachRemaining(buildList::add);
        return buildList;
    }

    public static String fetchProjectName(String buildConfigurationId) {
        return client
                .project(new ProjectId(buildConfigurationId))
                .getName();
    }

    public static void test2(List<Project> projects) {
//        var p = TeamCityInstanceFactory
//                .guestAuth(bundle.getString("url"))
//                .project(new ProjectId(buildConfigurationId));
//
//        p.getChildProjects();
//        p.getBuildConfigurations();
//
//        if (projects.isEmpty()) return;
    }

//    public static List<Build> getTests(String typeId) {
//        return TeamCityInstanceFactory
//                .guestAuth(TC_URL)
//                .testRuns()
//                .forBuild("");
//    }

//    ResourceFetcher
//            .fetchBuildList(buildConfigurationId)
//            .get(0)
//        .testRuns(TestStatus.FAILED)
//        .iterator()
//        .next()

}
