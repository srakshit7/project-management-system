package com.pms.model;

/** A web-development engagement (e.g. company websites, web apps). */
public class WebDevelopmentProject extends Project {

    public WebDevelopmentProject() {
        super();
    }

    public WebDevelopmentProject(Long id, String name, Client client, Requirement requirement) {
        super(id, name, client, requirement);
    }

    @Override
    public double calculateBillingRate() {
        return 500.0; // per hour
    }

    @Override
    public String getProjectType() {
        return "Web Development";
    }
}
