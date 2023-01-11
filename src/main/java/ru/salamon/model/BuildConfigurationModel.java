package ru.salamon.model;

import java.util.Set;

public class BuildConfigurationModel extends TreeModel {

    private String name;
    private Set<BuildModel> buildModels;

    public BuildConfigurationModel(String name, Set<BuildModel> buildModels) {
        this.name = name;
        this.buildModels = buildModels;
    }

    public String getName() {
        return name;
    }

    public Set<BuildModel> getBuildModels() {
        return buildModels;
    }
}
