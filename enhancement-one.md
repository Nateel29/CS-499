---
layout: default
title: Enhancement One — Software Design and Engineering
---

# Enhancement One: Software Design and Engineering

## Summary

The original artifact worked correctly but had no architecture: all classes lived in one flat package, services directly managed their own HashMap storage, and there was no external interface. This enhancement refactored the application into a **layered architecture** without changing any behavior.

## What Was Done

- **Domain layer** — Contact, Task, Appointment with all original validation preserved
- **Repository layer** — `ContactRepository`, `TaskRepository`, `AppointmentRepository` interfaces with in-memory implementations (Repository pattern)
- **Service layer** — business logic with constructor-based dependency injection
- **API layer** — `ContactController`, `TaskController`, `AppointmentController` REST controllers
- **DTO layer** — `ContactDto`, `TaskDto`, `AppointmentDto` decoupling the domain model from API contracts

## Key Design Decision

The Repository pattern means services depend on *interfaces*, not concrete storage. This felt like over-engineering at first — but it is exactly what made Enhancement Three (swapping in SQLite) possible with zero changes to business logic.

## Artifacts

- [Enhanced source code](artifacts/enhanced/src/main/java/com/grandstrand/)
- [Original source code](artifacts/original/) for comparison
- [Enhancement One Narrative (Word)](narratives/Enhancement_One_Software_Design_Narrative.docx)

## Skills Demonstrated

Software architecture design, design patterns (Repository, DI, DTO), separation of concerns, API design, behavior-preserving refactoring — **Course Outcomes 3 and 4**.
