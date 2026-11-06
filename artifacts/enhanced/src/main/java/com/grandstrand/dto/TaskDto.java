package com.grandstrand.dto;

/**
 * Data Transfer Object for Task API responses.
 */
public class TaskDto {
    private String taskId;
    private String name;
    private String description;

    public TaskDto() {}

    public TaskDto(String taskId, String name, String description) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
    }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
