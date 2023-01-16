package ru.salamon.model;

import java.util.Set;

public class BuildModel {

    private final String buildName;
    private final Set<TestRunModel> testRuns;


    public BuildModel(String buildName, Set<TestRunModel> testRuns) {
        this.buildName = buildName;
        this.testRuns = testRuns;
    }

    public String getBuildName() {
        return buildName;
    }

    public Set<TestRunModel> getTestRuns() {
        return testRuns;
    }
}
