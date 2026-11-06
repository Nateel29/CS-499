package com.grandstrand.domain;

/**
 * Task domain entity with validation.
 * Enhanced from original CS 320 version with proper package structure.
 */
public class Task {
    public static final int MAX_TASK_ID_LENGTH = 10;
    public static final int MAX_NAME_LENGTH = 20;
    public static final int MAX_DESCRIPTION_LENGTH = 50;

    private final String taskId;
    private String name;
    private String description;

    public Task(String taskId, String name, String description) {
        validateTaskId(taskId);
        this.taskId = taskId;
        validateName(name);
        this.name = name;
        validateDescription(description);
        this.description = description;
    }

    private void validateTaskId(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        if (taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be empty");
        }
        if (taskId.length() > MAX_TASK_ID_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Task ID cannot be longer than %d characters (provided: %d)",
                    MAX_TASK_ID_LENGTH, taskId.length()));
        }
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Name cannot be longer than %d characters (provided: %d)",
                    MAX_NAME_LENGTH, name.length()));
        }
    }

    private void validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Description cannot be longer than %d characters (provided: %d)",
                    MAX_DESCRIPTION_LENGTH, description.length()));
        }
    }

    public String getTaskId() { return taskId; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Task{taskId='%s', name='%s', description='%s'}",
            taskId, name, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return taskId.equals(task.taskId);
    }

    @Override
    public int hashCode() {
        return taskId.hashCode();
    }
}
