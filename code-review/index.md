---
layout: default
title: Code Review
---

# Code Review — Grand Strand Systems Artifact

Before beginning the enhancements, I performed a structured code review of the original CS 320 artifact using the CS 499 Code Review Checklist (structure, documentation, variables, arithmetic operations, loops and branches, defensive programming).

## Existing Functionality

The original artifact consists of three domain classes (Contact, Task, Appointment) and three service classes providing in-memory CRUD operations with strict input validation:

- **Contact**: ID (≤10 chars, immutable), names (≤10 chars), phone (exactly 10 digits), address (≤30 chars)
- **Task**: ID (≤10 chars, immutable), name (≤20 chars), description (≤50 chars)
- **Appointment**: ID (≤10 chars, immutable), date (must be future), description (≤50 chars)
- All services use HashMap storage with unique-ID enforcement
- 73 JUnit 5 tests with over 90% coverage

## Code Analysis — Areas for Improvement

| Checklist Area | Finding |
|---|---|
| Structure | Flat package; services mix storage and business logic; no separation of concerns |
| Efficiency | All searches are O(n) linear scans; no indexes or ordered structures |
| Functionality | No persistence — all data lost on exit; no external interface |
| Security | Input validation present, but no defense against injection (no database existed to inject into) |
| Testing | Strong coverage, but tests coupled to concrete storage implementations |

## Planned Enhancements

1. **Software Design and Engineering** — layered architecture, Repository pattern, dependency injection, DTOs, REST API controllers
2. **Algorithms and Data Structures** — TreeMap date index (O(log n) range queries), HashMap name index (O(1)), benchmark harness
3. **Databases** — SQLite persistence via JDBC, DAO pattern, PreparedStatements, transactions, schema constraints

## Video

*[Code review video — to be embedded/linked here. The written analysis above and the three enhancement narratives document the review findings and their resolution in full.]*

---

**Outcome alignment:** This review addresses Outcome 2 (professional communication) and Outcome 3 (evaluating computing solutions) by systematically identifying weaknesses and tying each to a planned, measurable enhancement.
