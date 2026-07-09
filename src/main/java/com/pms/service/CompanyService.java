package com.pms.service;

import com.pms.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents the Company: holds all clients, employees, and projects,
 * and implements the assignment / tracking / billing / feedback logic.
 * Backed by in-memory maps so no database setup is required for the lab.
 */
@Service
public class CompanyService {

    private final Map<Long, Client> clients = new ConcurrentHashMap<>();
    private final Map<Long, Employee> employees = new ConcurrentHashMap<>();
    private final Map<Long, Project> projects = new ConcurrentHashMap<>();

    private final AtomicLong clientIds = new AtomicLong(1);
    private final AtomicLong employeeIds = new AtomicLong(1);
    private final AtomicLong projectIds = new AtomicLong(1);
    private final AtomicLong feedbackIds = new AtomicLong(1);

    // ---------- Clients ----------

    public Client addClient(String name, String contact) {
        Client client = new Client(clientIds.getAndIncrement(), name, contact);
        clients.put(client.getId(), client);
        return client;
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients.values());
    }

    public Client getClient(Long id) {
        Client c = clients.get(id);
        if (c == null) throw new NoSuchElementException("Client not found: " + id);
        return c;
    }

    // ---------- Employees ----------

    public Employee addEmployee(String name, String designation, List<String> skills) {
        Employee employee = new Employee(employeeIds.getAndIncrement(), name, designation, skills);
        employees.put(employee.getId(), employee);
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public Employee getEmployee(Long id) {
        Employee e = employees.get(id);
        if (e == null) throw new NoSuchElementException("Employee not found: " + id);
        return e;
    }

    // ---------- Projects ----------

    /** type must be one of: WEB, MOBILE, INFRA */
    public Project createProject(String name, Long clientId, String type, String requirementDescription, List<String> featureList) {
        Client client = getClient(clientId);
        Requirement requirement = new Requirement(requirementDescription, featureList);
        Long id = projectIds.getAndIncrement();

        Project project = switch (type.toUpperCase()) {
            case "WEB" -> new WebDevelopmentProject(id, name, client, requirement);
            case "MOBILE" -> new MobileAppProject(id, name, client, requirement);
            case "INFRA" -> new InfrastructureProject(id, name, client, requirement);
            default -> throw new IllegalArgumentException("Unknown project type: " + type);
        };

        projects.put(id, project);
        return project;
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects.values());
    }

    public Project getProject(Long id) {
        Project p = projects.get(id);
        if (p == null) throw new NoSuchElementException("Project not found: " + id);
        return p;
    }

    // ---------- Assignment: which employee handles which project ----------

    public Project assignEmployeeToProject(Long projectId, Long employeeId) {
        Project project = getProject(projectId);
        Employee employee = getEmployee(employeeId);
        project.assignEmployee(employee);
        return project;
    }

    // ---------- Development tracker (lifecycle) ----------

    public Project advanceStage(Long projectId) {
        Project project = getProject(projectId);
        project.getTracker().advanceStage();
        return project;
    }

    // ---------- Billing ----------

    public Project logHours(Long projectId, double hours) {
        Project project = getProject(projectId);
        project.getBilling().addHours(hours);
        return project;
    }

    public Project markPaid(Long projectId) {
        Project project = getProject(projectId);
        project.getBilling().markPaid();
        return project;
    }

    // ---------- Client feedback (at each stage) ----------

    public Project addFeedback(Long projectId, String comment, int rating) {
        Project project = getProject(projectId);
        Feedback feedback = new Feedback(
                feedbackIds.getAndIncrement(),
                project.getTracker().getCurrentStage(),
                comment,
                rating
        );
        project.getFeedbackList().add(feedback);
        return project;
    }
}
