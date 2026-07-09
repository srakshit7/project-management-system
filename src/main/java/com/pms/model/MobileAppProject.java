package com.pms.model;

/** A mobile app engagement (Android/iOS). */
public class MobileAppProject extends Project {

    public MobileAppProject() {
        super();
    }

    public MobileAppProject(Long id, String name, Client client, Requirement requirement) {
        super(id, name, client, requirement);
    }

    @Override
    public double calculateBillingRate() {
        return 700.0; // per hour
    }

    @Override
    public String getProjectType() {
        return "Mobile App";
    }
}
