---
layout: default
title: Enhancement Three — Databases
---

# Enhancement Three: Databases

## Summary

The original artifact had no persistence — all data vanished on exit. This enhancement added a **SQLite persistence layer via JDBC** using the DAO pattern, with defense-in-depth validation and SQL-injection prevention throughout.

## What Was Done

- **Schema** (`schema.sql`) — normalized `contacts`, `tasks`, `appointments` tables with primary keys, CHECK constraints mirroring domain validation, and indexes on `appointments(appo_date)` and `tasks(name)`
- **DAO implementations** — `ContactDaoSqlite`, `TaskDaoSqlite`, `AppointmentDaoSqlite` implement the same Repository interfaces from Enhancement One
- **`DatabaseManager`** — centralized connection lifecycle and transactional schema initialization (commit/rollback)
- **Security** — every query uses parameterized PreparedStatements; a dedicated test proves `' OR '1'='1` injection attempts are neutralized
- **`PersistenceDemo`** — run twice: the second run finds the first run's data

## Verification

- **34/34 tests pass** — 24 service tests + 10 DAO tests (including injection safety and constraint enforcement)
- **Zero service-layer changes** were needed to swap in-memory storage for SQLite — the Repository pattern from Enhancement One made the database a drop-in replacement

## Artifacts

- [DAO layer source](artifacts/enhanced/src/main/java/com/grandstrand/dao/)
- [schema.sql](artifacts/enhanced/src/main/resources/schema.sql)
- [PersistenceDemo.java](artifacts/enhanced/src/main/java/com/grandstrand/PersistenceDemo.java)
- [Enhancement Three Narrative (Word)](narratives/Enhancement_Three_Databases_Narrative.docx)

## Skills Demonstrated

Relational schema design, SQL, JDBC, DAO pattern, transaction management, SQL-injection prevention, defense in depth — **Course Outcomes 4 and 5**.
