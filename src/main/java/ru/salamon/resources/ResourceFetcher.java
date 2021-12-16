package ru.salamon.resources;

import kotlin.sequences.Sequence;
import org.jetbrains.teamcity.rest.Build;
import org.jetbrains.teamcity.rest.BuildConfigurationId;
import org.jetbrains.teamcity.rest.TeamCityInstanceFactory;
import org.jetbrains.teamcity.rest.TestId;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ResourceFetcher {


    public static List<Build> fetchBuildList(String typeId) {
        var docs = new BuildConfigurationId(typeId);
        var buildList = new ArrayList<Build>();
        TeamCityInstanceFactory.guestAuth("https://buildserver.labs.intellij.net").builds()
                .fromConfiguration(docs)
                .since(Instant.now().minus(Duration.ofDays(1)))
                .all()
                .iterator()
                .forEachRemaining(buildList::add);
        return buildList;
    }

    public static void a(String testId) {
        var buildList = new ArrayList<Build>();
        TeamCityInstanceFactory.guestAuth("https://buildserver.labs.intellij.net");
    }


}
