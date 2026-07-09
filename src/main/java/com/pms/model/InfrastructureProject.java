package com.pms.model;

/** An infrastructure/DevOps engagement (servers, cloud, networking). */
public class InfrastructureProject extends Project {

    public InfrastructureProject() {
        super();
    }

    public InfrastructureProject(Long id, String name, Client client, Requirement requirement) {
        super(id, name, client, requirement);
    }

    @Override
    public double calculateBillingRate() {
        return 900.0; // per hour
    }

    @Override
    public String getProjectType() {
        return "Infrastructure";
    }
}
