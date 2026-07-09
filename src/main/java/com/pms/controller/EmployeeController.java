package com.pms.controller;

import com.pms.dto.EmployeeRequest;
import com.pms.model.Employee;
import com.pms.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final CompanyService companyService;

    public EmployeeController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public Employee create(@RequestBody EmployeeRequest request) {
        return companyService.addEmployee(request.getName(), request.getDesignation(), request.getSkills());
    }

    @GetMapping
    public List<Employee> all() {
        return companyService.getAllEmployees();
    }
}
