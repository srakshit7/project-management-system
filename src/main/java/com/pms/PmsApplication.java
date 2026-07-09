package com.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Project Management System.
 * A company handles multiple clients (projects) and assigns
 * a specific set of employees to manage particular projects.
 */
@SpringBootApplication
public class PmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PmsApplication.class, args);
        System.out.println("Project Management System running at http://localhost:8080");
    }
}
