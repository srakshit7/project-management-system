# Project Management System — OOP / UML Lab Assignment

A company manages multiple clients (projects) and assigns specific employees to
each project. Backend: Java 17 + Spring Boot (REST API). Frontend: plain
HTML/CSS/JS served by the same app. UML: auto-generated from the code with
UMLDoclet, so the diagram always matches the real class design.

## 1. Install what you need (one-time setup)

1. **Java 17 (JDK)** — check with `java -version` in a terminal. If missing,
   install "Temurin 17" from https://adoptium.net.
2. **Apache Maven** — check with `mvn -version`. If missing, install from
   https://maven.apache.org/download.cgi (or via `choco install maven` /
   `brew install maven`, depending on your OS).
3. **VS Code extensions** — open VS Code → Extensions (Ctrl+Shift+X) → install:
   - **Extension Pack for Java** (by Microsoft)
   - **Spring Boot Extension Pack** (by VMware / Microsoft)

## 2. Open the project

1. Unzip the project folder you downloaded.
2. In VS Code: File → Open Folder → select the `pms` folder (the one
   containing `pom.xml`).
3. Wait a few seconds — VS Code will detect `pom.xml` and download
   dependencies automatically (needs internet the first time).

## 3. Run it

**Option A — VS Code UI:** open `src/main/java/com/pms/PmsApplication.java`,
click the ▷ "Run" button that appears above the `main` method.

**Option B — terminal:** inside the `pms` folder run:
```
mvn spring-boot:run
```

Either way, once you see `Project Management System running at
http://localhost:8080`, open that URL in your browser. That's your frontend —
it's a single Spring Boot app serving both the API and the UI, so there's
nothing extra to start.

## 4. Use the app

1. Add a **Client** first (Clients tab → New client).
2. Add a few **Employees** (Employees tab → New employee, comma-separate skills).
3. Go to **Projects** → New project → pick the client and a project type
   (Web / Mobile / Infrastructure — these map to the three `Project` subclasses).
4. Click **Manage** on a project card to:
   - Assign employees to the project
   - Advance it through the lifecycle (Requirement → Feasibility → Design →
     Testing → Deployment → Maintenance)
   - Log billed hours / mark paid
   - Add client feedback (tagged automatically with the current stage)

## 5. REST API reference (for your report / Postman screenshots)

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/clients` | create client `{name, contact}` |
| GET | `/api/clients` | list clients |
| POST | `/api/employees` | create employee `{name, designation, skills[]}` |
| GET | `/api/employees` | list employees |
| POST | `/api/projects` | create project `{name, clientId, type, requirementDescription, featureList[]}` — `type` is `WEB`, `MOBILE`, or `INFRA` |
| GET | `/api/projects` | list projects |
| GET | `/api/projects/{id}` | project detail |
| POST | `/api/projects/{id}/assign` | `{employeeId}` — assign employee to project |
| POST | `/api/projects/{id}/advance-stage` | move to next lifecycle stage |
| POST | `/api/projects/{id}/billing/hours` | `{hours}` — log billed hours |
| POST | `/api/projects/{id}/billing/pay` | mark billing as paid |
| POST | `/api/projects/{id}/feedback` | `{comment, rating}` — client feedback at current stage |

## 6. Generate the UML diagram from the code

This runs UMLDoclet over your actual `.java` files, so the diagram can never
drift from the code:
```
mvn javadoc:javadoc -P uml
```
Output appears in `target/site/apidocs/` — look for `.png` files per package
(one for `com.pms.model`, showing `Project` as the abstract superclass with
`WebDevelopmentProject`, `MobileAppProject`, `InfrastructureProject` beneath
it, plus its associations to `Client`, `Employee`, `Requirement`,
`DevelopmentTracker`, `Billing`, `Feedback`). Take a screenshot of this for
your report's UML section.

## 7. Test cases

Exercise these through the frontend or Postman, and record actual vs
expected in your report:

| # | Feature | Steps | Expected result |
|---|---|---|---|
| 1 | Client creation | Add client "Orbit Retail" | Appears in Clients list |
| 2 | Employee creation | Add employee with 2 skills | Skills render as chips |
| 3 | Project creation | Create a WEB project for that client | Card shows type "Web Development", stage = REQUIREMENT |
| 4 | Assignment | Assign 1 employee to the project | Employee appears under "Team assignment" |
| 5 | Lifecycle advance | Click "Advance to FEASIBILITY" repeatedly | Stage rail fills left→right, stops after MAINTENANCE |
| 6 | Billing | Log 10 hours on a WEB project (rate ₹500/hr) | Amount due shows ₹5000, status PARTIAL |
| 7 | Mark paid | Click "Mark paid" | Status changes to PAID |
| 8 | Feedback | Submit feedback at DESIGN stage | Feedback item tagged "DESIGN" appears |

## 8. Push to GitHub

```
cd pms
git init
git add .
git commit -m "Project management system - OOP + UML lab assignment"
git branch -M main
git remote add origin https://github.com/<your-username>/<repo-name>.git
git push -u origin main
```
Paste that GitHub link into your report.

## 9. Report structure (per the assignment)

Brief Introduction → Objective → UML Design (screenshot from step 6) →
Output screenshots (frontend + a Postman call) → Test cases (table above,
filled in) → GitHub link → optional frontend note (mention it's a Spring Boot
+ HTML/CSS/JS client-server app, running locally on `localhost:8080`).
