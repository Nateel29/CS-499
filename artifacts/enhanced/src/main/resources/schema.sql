-- Grand Strand Systems Database Schema
-- Milestone Four: Databases Enhancement
-- Normalized schema mirroring domain validation rules

PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS contacts (
    contact_id  TEXT PRIMARY KEY CHECK (length(contact_id) BETWEEN 1 AND 10),
    first_name  TEXT NOT NULL CHECK (length(first_name) <= 10),
    last_name   TEXT NOT NULL CHECK (length(last_name) <= 10),
    phone       TEXT NOT NULL CHECK (length(phone) = 10 AND phone GLOB '[0-9]*'),
    address     TEXT NOT NULL CHECK (length(address) <= 30)
);

CREATE TABLE IF NOT EXISTS tasks (
    task_id     TEXT PRIMARY KEY CHECK (length(task_id) BETWEEN 1 AND 10),
    name        TEXT NOT NULL CHECK (length(name) BETWEEN 1 AND 20),
    description TEXT NOT NULL CHECK (length(description) BETWEEN 1 AND 50)
);

CREATE TABLE IF NOT EXISTS appointments (
    appo_id     TEXT PRIMARY KEY CHECK (length(appo_id) BETWEEN 1 AND 10),
    appo_date   INTEGER NOT NULL,  -- stored as epoch milliseconds
    description TEXT NOT NULL CHECK (length(description) BETWEEN 1 AND 50),
    CHECK (appo_date > 0)
);

-- Indexes for query performance (carried over from Milestone Three analysis)
CREATE INDEX IF NOT EXISTS idx_appointments_date ON appointments(appo_date);
CREATE INDEX IF NOT EXISTS idx_tasks_name ON tasks(name);
