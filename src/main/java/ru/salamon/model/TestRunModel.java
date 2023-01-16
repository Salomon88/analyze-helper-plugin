package ru.salamon.model;

import java.util.Set;

public class TestRunModel {

    private final String name;
    private Set<String> metadata;

    public TestRunModel(String name, Set<String> metadata) {
        this.name = name;
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public Set<String> getMetadata() {
        return metadata;
    }
}
