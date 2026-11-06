/**
 * Task class represents a task object with unique ID, name, and description.
 * This class is designed for a mobile application task management system.
 * 
 * Requirements:
 * - Task ID: Required, unique, max 10 characters, not null, not updatable
 * - Name: Required, max 20 characters, not null, updatable
 * - Description: Required, max 50 characters, not null, updatable
 * 
 * @author CS-305 Student
 * @version 1.0
 */
public class Task {
    // Constants for validation
    public static final int MAX_TASK_ID_LENGTH = 10;
    public static final int MAX_NAME_LENGTH = 20;
    public static final int MAX_DESCRIPTION_LENGTH = 50;
    
    // Task fields
    private final String taskId;  // Required, unique, max 10 chars, not null, not updatable
    private String name;          // Required, max 20 chars, not null, updatable
    private String description;   // Required, max 50 chars, not null, updatable
    
    /**
     * Constructor to create a new Task object.
     * 
     * @param taskId The unique identifier for the task (max 10 characters, not null)
     * @param name The name of the task (max 20 characters, not null)
     * @param description The description of the task (max 50 characters, not null)
     * @throws IllegalArgumentException if any parameter violates the requirements
     */
    public Task(String taskId, String name, String description) {
        // Validate and set taskId
        validateTaskId(taskId);
        this.taskId = taskId;
        
        // Validate and set name
        validateName(name);
        this.name = name;
        
        // Validate and set description
        validateDescription(description);
        this.description = description;
    }
    
    /**
     * Validates the task ID according to requirements.
     * 
     * @param taskId The task ID to validate
     * @throws IllegalArgumentException if task ID is invalid
     */
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
    
    /**
     * Validates the task name according to requirements.
     * 
     * @param name The name to validate
     * @throws IllegalArgumentException if name is invalid
     */
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
    
    /**
     * Validates the task description according to requirements.
     * 
     * @param description The description to validate
     * @throws IllegalArgumentException if description is invalid
     */
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
    
    /**
     * Gets the task ID.
     * 
     * @return The task ID (immutable)
     */
    public String getTaskId() {
        return taskId;
    }
    
    /**
     * Gets the task name.
     * 
     * @return The task name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the task description.
     * 
     * @return The task description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the task name (updatable field).
     * 
     * @param name The new name for the task
     * @throws IllegalArgumentException if name violates requirements
     */
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }
    
    /**
     * Sets the task description (updatable field).
     * 
     * @param description The new description for the task
     * @throws IllegalArgumentException if description violates requirements
     */
    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }
    
    /**
     * Returns a string representation of the Task object.
     * 
     * @return String representation of the task
     */
    @Override
    public String toString() {
        return String.format("Task{taskId='%s', name='%s', description='%s'}", 
            taskId, name, description);
    }
    
    /**
     * Checks if this task is equal to another object.
     * Two tasks are equal if they have the same task ID.
     * 
     * @param obj The object to compare with
     * @return true if the tasks are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return taskId.equals(task.taskId);
    }
    
    /**
     * Returns the hash code for this task.
     * 
     * @return The hash code based on task ID
     */
    @Override
    public int hashCode() {
        return taskId.hashCode();
    }
}
