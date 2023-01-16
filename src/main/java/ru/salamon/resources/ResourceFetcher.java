package ru.salamon.resources;

import org.jetbrains.teamcity.rest.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ResourceFetcher {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("teamcity");
    private static final TeamCityInstance client = TeamCityInstanceFactory.guestAuth(bundle.getString("url"));

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

}
