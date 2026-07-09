package com.pms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee of the company who can be assigned to projects.
* Captures the employee-designation-skill tuple from the assignment brief.
 */
public class Employee {

    private Long id;
    private String name;
    private String designation;
    private List<String> skills = new ArrayList<>();

    public Employee() {
    }

    public Employee(Long id, String name, String designation, List<String> skills) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        if (skills != null) {
            this.skills = skills;
        }
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
