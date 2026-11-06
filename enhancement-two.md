---
layout: default
title: Enhancement Two — Algorithms and Data Structures
---

# Enhancement Two: Algorithms and Data Structures

## Summary

The original artifact stored entities in a HashMap (O(1) by ID) but performed every search as an **O(n) linear scan**. This enhancement added secondary indexes and proved the improvement with a benchmark harness.

## What Was Done

- **`IndexedAppointmentRepository`** — adds a `TreeMap<Date, List<Appointment>>` index for **O(log n + k)** date-range queries via `subMap()`
- **`IndexedTaskRepository`** — adds a `HashMap<String, List<Task>>` name index for **O(1)** exact-match lookups
- **`Benchmark.java`** — measures linear vs. indexed approaches at 100 / 1,000 / 10,000 records
- Both indexed repositories implement the same interfaces — the service layer needed no changes

## Benchmark Results

| Data Size | Date Range Query | Name Lookup |
|---|---|---|
| 100 | 1.0x (comparable) | **12.2x faster** |
| 1,000 | 0.9x (index overhead) | **52.2x faster** |
| 10,000 | **2.5x faster** | **193.3x faster** |

The results demonstrate an important lesson: indexes cost memory and insert-time overhead, so the benefit appears at scale — which is exactly when it matters.

## Artifacts

- [IndexedAppointmentRepository.java](artifacts/enhanced/src/main/java/com/grandstrand/repository/IndexedAppointmentRepository.java)
- [IndexedTaskRepository.java](artifacts/enhanced/src/main/java/com/grandstrand/repository/IndexedTaskRepository.java)
- [Benchmark.java](artifacts/enhanced/src/main/java/com/grandstrand/Benchmark.java)
- [Enhancement Two Narrative (Word)](narratives/Enhancement_Two_Algorithms_Narrative.docx)

## Skills Demonstrated

Big-O analysis, data structure selection with explicit trade-off reasoning (memory vs. query speed), empirical performance validation — **Course Outcome 3**.
