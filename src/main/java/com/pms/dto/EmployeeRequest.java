package com.pms.dto;

import java.util.List;

public class EmployeeRequest {
    private String name;
    private String designation;
    private List<String> skills;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
}
