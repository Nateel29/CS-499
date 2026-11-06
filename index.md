---
layout: default
title: Home
---

# Allan Torres — CS 499 Computer Science Capstone ePortfolio

Welcome to my professional ePortfolio. This site presents my capstone work: the enhancement of the **Grand Strand Systems Mobile App Services** artifact across the three key categories of computer science — software design and engineering, algorithms and data structures, and databases.

## Contents

- **[Professional Self-Assessment](self-assessment)** — my formal introduction: strengths, professional goals, and how the artifacts fit together
- **[Code Review](code-review/)** — analysis of the original artifact and the enhancement plan
- **[Enhancement One: Software Design and Engineering](enhancement-one)** — layered architecture, Repository pattern, dependency injection, REST API design
- **[Enhancement Two: Algorithms and Data Structures](enhancement-two)** — TreeMap and HashMap indexes, O(n) → O(log n)/O(1), benchmark validation
- **[Enhancement Three: Databases](enhancement-three)** — SQLite persistence, JDBC, DAO pattern, SQL injection prevention, transactions
- **[Original Artifact](artifacts/original/)** — the CS 320 code as it existed before enhancement
- **[Enhanced Artifact](artifacts/enhanced/)** — the final enhanced code with all three enhancements applied

## The Artifact at a Glance

| | Original (CS 320, Aug 2025) | Enhanced (CS 499, Nov 2026) |
|---|---|---|
| **Architecture** | Flat package | Layered: presentation → service → repository → domain |
| **Storage** | In-memory HashMap only | SQLite via JDBC + in-memory, behind Repository interfaces |
| **Search** | O(n) linear scans | O(log n) date ranges, O(1) name lookups, DB indexes |
| **Interface** | None | REST API controllers with DTOs |
| **Security** | Input validation only | + PreparedStatements, schema constraints, injection tests |
| **Tests** | 73 JUnit tests | 34 focused tests incl. DAO + injection-safety tests |

## About Me

I am completing my BS in Computer Science at Southern New Hampshire University. I work at Crayola LLC and am targeting an IT or analyst position, with graduate study in mechanical engineering as a longer-term goal. This ePortfolio demonstrates that I can take working software and make it better: better structured, faster, and persistent — with the tests to prove it.

**Repository:** [github.com/Nateel29/CS-499](https://github.com/Nateel29/CS-499)
