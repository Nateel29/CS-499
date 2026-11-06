---
layout: default
title: Professional Self-Assessment
---

# Professional Self-Assessment

**Allan Torres — BS in Computer Science, Southern New Hampshire University**

## Introduction

I began my computer science studies in 2021 at Lehigh Carbon Community College and joined SNHU in August 2024, earning my AS in Computer Science and accelerating through the BS program by taking three courses per module when my schedule allowed. Completing this program while working full-time at Crayola LLC taught me discipline, time management, and how to learn complex technical material independently — skills that matter as much to an employer as any specific technology.

## Showcasing My Strengths

The coursework and this ePortfolio together demonstrate my core strengths:

**Collaborating in a team environment.** Across the program I worked in Agile team settings, most notably in CS 250 (Software Development Lifecycle), where our team ran sprints with defined roles, stand-ups, and retrospectives. I learned that collaboration is not just dividing work — it is shared ownership of quality. In this capstone, I applied that lesson by using Git version control throughout the enhancement process, writing documentation for an audience, and structuring the work so another developer could pick it up without a handoff meeting.

**Communicating with stakeholders.** CS 255 (System Analysis and Design) trained me to translate between business language and technical language — gathering requirements from a client, documenting them unambiguously, and presenting solutions back in terms the client cares about. Every narrative in this ePortfolio practices that skill: each one explains not just *what* I built, but *why* the design decisions matter and what trade-offs they involve.

**Data structures and algorithms.** CS 300 gave me the foundation — Big-O analysis, trees, hash tables, and the discipline of choosing a structure based on access patterns rather than habit. Enhancement Two in this portfolio applies that directly: I replaced O(n) linear scans with a TreeMap date index (O(log n) range queries) and a HashMap name index (O(1) lookups), then built a benchmark harness to prove the improvement empirically — 193x faster name lookups at 10,000 records.

**Software engineering and databases.** CS 320 taught me that quality is a process, not a final check — I built the original artifact's 73-test JUnit suite there. DAD 220 gave me relational modeling and SQL, and CS 340 extended that to MongoDB and dashboard-driven data presentation. Enhancements One and Three bring these together: a layered architecture with the Repository pattern, and a SQLite persistence layer using JDBC with PreparedStatements throughout, transactional schema initialization, and CHECK constraints that mirror domain validation as defense in depth.

**Security.** CS 305 (Software Security) built my security mindset — anticipating how adversaries exploit software rather than trusting inputs. Enhancement Three demonstrates this concretely: every database query uses parameterized PreparedStatements, and the test suite includes a dedicated SQL-injection test proving that a classic `' OR '1'='1` attack is neutralized.

## Professional Goals and Values

My immediate goal is to move into an IT or analyst position at Crayola LLC, where I currently work. This ePortfolio is built for that audience: it shows I can take an existing system, analyze its weaknesses, and improve it measurably — exactly what internal IT and analyst roles demand. Longer term, I plan to attend graduate school for mechanical engineering, where the computational and algorithmic foundation from this program transfers directly to engineering simulation and analysis work.

I value work that is verifiable. Anyone can claim skills on a resume; this portfolio provides the code, the tests, the benchmarks, and the reasoning. I also value finishing what I start: I accelerated through this program in two years while working full-time, and this capstone — one artifact enhanced across all three categories, with every milestone building on the last — reflects that same approach.

## How the Artifacts Fit Together

This portfolio contains a single artifact — the **Grand Strand Systems Mobile App Services** from CS 320 — enhanced across all three categories, which is itself a demonstration of integrated skill:

1. **Enhancement One (Software Design and Engineering)** transformed the flat original code into a layered architecture with the Repository pattern, dependency injection, DTOs, and REST API controllers — without changing any behavior.
2. **Enhancement Two (Algorithms and Data Structures)** added TreeMap and HashMap indexes to the repository layer, with a benchmark harness proving the performance gains.
3. **Enhancement Three (Databases)** swapped the in-memory repositories for SQLite DAO implementations — with zero changes to the service layer, proving the value of the Milestone Two architecture.

The three enhancements are not separate projects bolted together; each one depends on and validates the one before it. The architecture made the algorithms easy to add. The interfaces made the database a drop-in replacement. That is the story of this portfolio: I do not just write code that works — I build software that can grow.

The sections that follow present the code review, the original artifact, each enhanced artifact with its narrative, and the final enhanced code. I invite you to start with whichever piece interests you most.

---

**Contact:** [github.com/Nateel29](https://github.com/Nateel29)
