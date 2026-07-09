package com.pms.controller;

import com.pms.dto.*;
import com.pms.model.Project;
import com.pms.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final CompanyService companyService;

    public ProjectController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public Project create(@RequestBody ProjectRequest request) {
        return companyService.createProject(
                request.getName(),
                request.getClientId(),
                request.getType(),
                request.getRequirementDescription(),
                request.getFeatureList()
        );
    }

    @GetMapping
    public List<Project> all() {
        return companyService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project one(@PathVariable Long id) {
        return companyService.getProject(id);
    }

    @PostMapping("/{id}/assign")
    public Project assign(@PathVariable Long id, @RequestBody AssignRequest request) {
        return companyService.assignEmployeeToProject(id, request.getEmployeeId());
    }

    @PostMapping("/{id}/advance-stage")
    public Project advanceStage(@PathVariable Long id) {
        return companyService.advanceStage(id);
    }

    @PostMapping("/{id}/billing/hours")
    public Project logHours(@PathVariable Long id, @RequestBody HoursRequest request) {
        return companyService.logHours(id, request.getHours());
    }

    @PostMapping("/{id}/billing/pay")
    public Project markPaid(@PathVariable Long id) {
        return companyService.markPaid(id);
    }

    @PostMapping("/{id}/feedback")
    public Project addFeedback(@PathVariable Long id, @RequestBody FeedbackRequest request) {
        return companyService.addFeedback(id, request.getComment(), request.getRating());
    }
}
