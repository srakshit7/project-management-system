package com.pms.controller;

import com.pms.dto.ClientRequest;
import com.pms.model.Client;
import com.pms.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    private final CompanyService companyService;

    public ClientController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public Client create(@RequestBody ClientRequest request) {
        return companyService.addClient(request.getName(), request.getContact());
    }

    @GetMapping
    public List<Client> all() {
        return companyService.getAllClients();
    }
}
