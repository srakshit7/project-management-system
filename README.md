# Project Management System — OOP / UML Lab Assignment

An object-oriented Project Management System where a company manages multiple
clients, each with one or more projects, and assigns specific employees to
each project.Backend: Java 17 + Spring Boot (REST API). Frontend: plain
HTML/CSS/JS served by the same app. UML: auto-generated from the code with
UMLDoclet, so the diagram always matches the real class design.

## Features
- Employee-to-project assignment (employee, designation, skill)
- Project requirement tracking
- Development lifecycle tracker (Requirement → Feasibility → Design → Testing → Deployment → Maintenance)
- Project billing (rate, hours, amount due, payment status)
- Client feedback captured at each lifecycle stage

## Tech Stack
- **Backend:** Java 17, Spring Boot, REST API
- **Frontend:** HTML, CSS, JavaScript
- **UML:** Generated from the actual class design

## Architecture
`Project` is an abstract superclass extended by `WebDevelopmentProject`,
`MobileAppProject`, and `InfrastructureProject`, each with its own billing
rate. Every project is composed of a `Requirement`, `DevelopmentTracker`,
`Billing`, and a list of `Feedback` entries, and is linked to a `Client`
and a team of `Employee` objects.

!project.png



