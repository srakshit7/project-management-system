package com.pms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the general requirement text for a project, plus an optional
 * itemised feature list. This is a composition of Project: a Requirement
 * cannot meaningfully exist without its owning Project.
 */
public class Requirement {

    private String description;
    private List<String> featureList = new ArrayList<>();

    public Requirement() {
    }

    public Requirement(String description, List<String> featureList) {
        this.description = description;
        if (featureList != null) {
            this.featureList = featureList;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<String> featureList) {
        this.featureList = featureList;
    }
}
