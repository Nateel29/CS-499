# Grand Strand Systems — Enhanced Architecture

## CS 499 Milestone Four: Databases Enhancement

**Author:** Allan Torres  
**Course:** CS 499 — Computer Science Capstone  
**Date:** November 2026  
**Original Artifact:** CS 320 (Software Test Automation & QA), August 2025

---

## Enhancement Overview

This project enhances the original CS 320 Grand Strand Systems artifact by refactoring it from a flat class structure into a **layered architecture** with formal design patterns. The enhancement demonstrates software engineering principles including separation of concerns, dependency injection, and the Repository pattern.

## Architecture

```
┌─────────────────────────────────────┐
│  Presentation Layer (API)           │  ← ContactController, TaskController, AppointmentController
│  - REST endpoints                   │
│  - DTOs for API contracts           │
├─────────────────────────────────────┤
│  Service Layer                      │  ← ContactService, TaskService, AppointmentService
│  - Business logic                   │
│  - Dependency injection             │
├─────────────────────────────────────┤
│  Repository Layer                   │  ← ContactRepository, TaskRepository, AppointmentRepository
│  - Storage abstraction              │
│  - In-memory implementations        │
├─────────────────────────────────────┤
│  Domain Layer                       │  ← Contact, Task, Appointment
│  - Entity validation                │
│  - Business rules                   │
└─────────────────────────────────────┘
```

## Design Patterns Applied

1. **Repository Pattern** — Abstracts storage behind interfaces (`ContactRepository`, `TaskRepository`, `AppointmentRepository`) so the service layer does not depend on concrete storage implementations.

2. **Dependency Injection** — Services receive their repository dependencies through constructors, enabling testability and flexibility.

3. **DTO (Data Transfer Object) Pattern** — `ContactDto`, `TaskDto`, `AppointmentDto` decouple the internal domain model from external API contracts.

4. **Layered Architecture** — Clear separation between presentation, business logic, and data access.

## Project Structure

```
enhanced/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── grandstrand/
│   │               ├── domain/           # Entities with validation
│   │               │   ├── Contact.java
│   │               │   ├── Task.java
│   │               │   └── Appointment.java
│   │               ├── repository/       # Storage abstraction
│   │               │   ├── ContactRepository.java
│   │               │   ├── InMemoryContactRepository.java
│   │               │   ├── TaskRepository.java
│   │               │   ├── InMemoryTaskRepository.java
│   │               │   ├── AppointmentRepository.java
│   │               │   └── InMemoryAppointmentRepository.java
│   │               ├── service/          # Business logic
│   │               │   ├── ContactService.java
│   │               │   ├── TaskService.java
│   │               │   └── AppointmentService.java
│   │               ├── api/              # Presentation layer
│   │               │   ├── ContactController.java
│   │               │   ├── TaskController.java
│   │               │   └── AppointmentController.java
│   │               ├── dto/              # Data transfer objects
│   │               │   ├── ContactDto.java
│   │               │   ├── TaskDto.java
│   │               │   └── AppointmentDto.java
│   │               └── Application.java  # Main entry point
│   └── test/
│       └── java/
│           └── com/
│               └── grandstrand/
│                   ├── ContactServiceTest.java
│                   ├── TaskServiceTest.java
│                   └── AppointmentServiceTest.java
└── README.md
```

## Key Improvements Over Original

| Aspect | Original (CS 320) | Enhanced (CS 499) |
|--------|-------------------|-------------------|
| **Structure** | Flat package, all classes in one folder | Layered packages (domain, repository, service, api, dto) |
| **Storage** | Services directly manage HashMap | Repository interfaces with in-memory implementations |
| **Coupling** | Services tightly coupled to storage | Services depend on abstractions (interfaces) |
| **Testability** | Tests instantiate services directly | Tests can inject mock repositories |
| **API** | No external interface | REST controllers with DTOs |
| **Extensibility** | Difficult to swap storage | Easy to add database implementations |

## What Stayed the Same

- All validation rules from the original requirements
- All CRUD operations and business logic
- JUnit 5 test coverage
- In-memory storage behavior (for now — database enhancement comes in Module 6)

## Next Enhancements

- **Module 4 (Algorithms):** COMPLETE � TreeMap date index, HashMap name index, benchmark harness
- **Module 5 (Databases):** COMPLETE � SQLite persistence layer with JDBC, DAO pattern, PreparedStatements, transactions, database indexes

## Database Layer (Milestone Four)

- **SQLite** database via JDBC (xerial driver)
- **DAO pattern**: `ContactDaoSqlite`, `TaskDaoSqlite`, `AppointmentDaoSqlite` implement the same Repository interfaces from Milestone Two � services work unchanged
- **Schema** (`src/main/resources/schema.sql`): normalized tables with CHECK constraints mirroring domain validation, plus indexes on `appointments(appo_date)` and `tasks(name)` carrying the Milestone Three algorithm work into the database
- **Security**: all queries use PreparedStatements (no string concatenation) � SQL-injection safe, verified by `testSqlInjectionSafe`
- **Transactions**: schema initialization uses commit/rollback; connection lifecycle managed by `DatabaseManager`
- **Proof of persistence**: run `PersistenceDemo` twice � the second run finds data from the first

```bash
# Compile
javac -d target/classes -cp "lib/sqlite-jdbc-3.46.1.3.jar" -sourcepath src/main/java src/main/java/com/grandstrand/**/*.java

# Run persistence demo
java -cp "target/classes;lib/sqlite-jdbc-3.46.1.3.jar" com.grandstrand.PersistenceDemo

# Run tests (34 tests: 24 service + 10 DAO)
java -jar lib/junit-platform-console-standalone-1.10.5.jar execute --class-path "target/classes;target/test-classes;lib/sqlite-jdbc-3.46.1.3.jar" --scan-classpath
```

## Building and Running

```bash
# Compile
javac -d target/classes src/main/java/com/grandstrand/**/*.java

# Run application
java -cp target/classes com.grandstrand.Application

# Run tests (requires JUnit 5)
javac -cp junit-platform-console-standalone.jar src/test/java/com/grandstrand/*.java
java -cp junit-platform-console-standalone.jar:target/classes org.junit.platform.console.ConsoleLauncher --scan-classpath
```

## Notes

- The REST API controllers are currently framework-agnostic. A full implementation would use Javalin or Spring Boot to expose actual HTTP endpoints.
- The in-memory repositories preserve the original CS 320 behavior while implementing the new interfaces.
- All original validation logic is preserved in the domain layer.
