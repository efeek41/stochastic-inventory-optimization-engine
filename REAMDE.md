# Stochastic Continuous-Review (Q, R) Inventory Optimization Engine

Interdisciplinary software project developed at Eskişehir Technical University for **BİM2006 (Computer Programming IV)** and **ENM320 (Production & Operations Planning II)**.

---

## Overview
This engine provides an automated numerical solver for the continuous-review $(Q, R)$ stochastic inventory model under uncertain lead-time demand. The system computes the cost-minimizing Order Quantity ($Q$) and Reorder Point ($R$) via an iterative convergence algorithm. By implementing continuous numerical approximations (Peter J. Acklam's inverse normal CDF and Gauss `erf`), it eliminates the reliance on discrete printed statistical lookup tables.

---

## Repository Structure
```text
stochastic-inventory-optimization-engine/
├── .gitignore
├── pom.xml
├── README.md
├── docs/
│   ├── mathematical-model-and-manual-solution.pdf
│   ├── stochastic-optimization-technical-report.pdf
│   └── project-management-and-work-packages.pdf
└── src/
    └── main/
        └── java/
            └── estu/
                └── ceng/
                    └── group2/
                        ├── InventoryOptimizer.java
                        └── InventoryStatistics.java
```

---

## Documentation
All theoretical derivations, manual benchmark calculations, and project tracking records are archived in the [`docs/`](docs/) directory:
* [`docs/stochastic-optimization-technical-report.pdf`](docs/stochastic-optimization-technical-report.pdf): Implementation architecture, continuous statistical approximations, and execution outputs.
* [`docs/mathematical-model-and-manual-solution.pdf`](docs/mathematical-model-and-manual-solution.pdf): Operations research formulation, step-by-step manual solution, and algorithm pseudocode.
* [`docs/project-management-and-work-packages.pdf`](docs/project-management-and-work-packages.pdf): Work breakdown structure, Gantt timeline, and official work package distributions.

---

## Team & Responsibilities

### Computer Engineering (BİM2006)
* **Efe Kemal Yılmaz:** Work Package Leader (WP1: Project Planning & WP3: Statistical Infrastructure & Numerical Approximations), Core Algorithm Development.
* **Fatih Furkan Şahin:** Work Package Leader (WP4: OR Optimization Algorithm Implementation).
* **Ahmet Can Çavdar, İbrahim Alp Karınca:** Core Java Development, Numerical Approximations (`erf`, Acklam), and Maven Configuration.

### Industrial Engineering (ENM320)
* **Emre Görücü:** Work Package Leader (WP2: Problem Analysis & Manual Hand Solution).
* **Arda Söğüt:** Work Package Leader (WP5: Software Verification & User Documentation).
* **Yunus Emre Özdemir:** Stochastic Model Derivation and UN SDG Sustainability Assessment.

---

## Build & Execution

### Prerequisites
* JDK 23
* Apache Maven

### Packaging and Running
1. Package the project into an executable JAR:
   ```bash
   mvn clean package
   ```
2. Execute the compiled application:
   ```bash
   java -jar target/Project-1.0.jar
   ```

---

## References
* Peter J. Acklam, *An algorithm for computing the inverse normal cumulative distribution function*.
* Continuous-Review Stochastic $(Q, R)$ Inventory Model & *Harvey's Specialty Shop* Case Study.

---
