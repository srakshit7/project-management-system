package com.pms.dto;

import java.util.List;

public class ProjectRequest {
    private String name;
    private Long clientId;
    /** One of: WEB, MOBILE, INFRA */
    private String type;
    private String requirementDescription;
    private List<String> featureList;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getRequirementDescription() { return requirementDescription; }
    public void setRequirementDescription(String requirementDescription) { this.requirementDescription = requirementDescription; }
    public List<String> getFeatureList() { return featureList; }
    public void setFeatureList(List<String> featureList) { this.featureList = featureList; }
}
