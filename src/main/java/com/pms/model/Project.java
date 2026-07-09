package com.pms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract superclass for every kind of project the company runs.
 * This is the "Super class: PROJECT" from the assignment brief.
 * Concrete subclasses (WebDevelopmentProject, MobileAppProject,
 * InfrastructureProject) each define their own billing rate and type label,
 * demonstrating polymorphism over this shared contract.
 */
public abstract class Project {

    private Long id;
    private String name;
    private Client client;
    private Requirement requirement;
    private List<Employee> assignedTeam = new ArrayList<>();
    private DevelopmentTracker tracker = new DevelopmentTracker();
    private Billing billing;
    private List<Feedback> feedbackList = new ArrayList<>();

    protected Project() {
    }

    protected Project(Long id, String name, Client client, Requirement requirement) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.requirement = requirement;
        this.billing = new Billing(calculateBillingRate());
    }

    /** Hourly billing rate — differs per project type. Implemented by each subclass. */
    public abstract double calculateBillingRate();

    /** Human-readable project type, e.g. "Web Development". Implemented by each subclass. */
    public abstract String getProjectType();

    public void assignEmployee(Employee employee) {
        assignedTeam.add(employee);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public List<Employee> getAssignedTeam() {
        return assignedTeam;
    }

    public void setAssignedTeam(List<Employee> assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    public DevelopmentTracker getTracker() {
        return tracker;
    }

    public void setTracker(DevelopmentTracker tracker) {
        this.tracker = tracker;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
