import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * TaskService class provides in-memory storage and management for Task objects.
 * This service supports adding, updating, deleting, and retrieving tasks.
 * 
 * Requirements:
 * - Add tasks with unique IDs
 * - Delete tasks by task ID
 * - Update task fields (name and description) by task ID
 * - Use in-memory data structures (no database required)
 * 
 * @author Allan Torres
 * @date July 30, 2025  
 * @course CS-320-11659-M01
 * @version 1.0
 */
public class TaskService {
    // In-memory storage for tasks using HashMap for O(1) lookup
    private final Map<String, Task> tasks;
    
    /**
     * Constructor initializes the TaskService with an empty task collection.
     */
    public TaskService() {
        this.tasks = new HashMap<>();
    }
    
    /**
     * Adds a new task with the specified parameters.
     * The task ID must be unique across all tasks in the service.
     * 
     * @param taskId The unique identifier for the task
     * @param name The name of the task
     * @param description The description of the task
     * @throws IllegalArgumentException if task ID already exists or if any parameter is invalid
     */
    public void addTask(String taskId, String name, String description) {
        // Check for null task ID before checking existence
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        
        // Check if task ID already exists
        if (tasks.containsKey(taskId)) {
            throw new IllegalArgumentException(
                String.format("Task with ID '%s' already exists", taskId));
        }
        
        // Create new task (Task constructor will validate all parameters)
        Task newTask = new Task(taskId, name, description);
        tasks.put(taskId, newTask);
    }
    
    /**
     * Adds a Task object directly to the service.
     * 
     * @param newTask The Task object to add
     * @throws IllegalArgumentException if task is null or task ID already exists
     */
    public void addTask(Task newTask) {
        if (newTask == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        
        String taskId = newTask.getTaskId();
        if (tasks.containsKey(taskId)) {
            throw new IllegalArgumentException(
                String.format("Task with ID '%s' already exists", taskId));
        }
        
        tasks.put(taskId, newTask);
    }
    
    /**
     * Deletes a task by its task ID.
     * 
     * @param taskId The ID of the task to delete
     * @throws IllegalArgumentException if task ID is null or task is not found
     */
    public void deleteTask(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        
        if (!tasks.containsKey(taskId)) {
            throw new IllegalArgumentException(
                String.format("Task with ID '%s' not found", taskId));
        }
        
        tasks.remove(taskId);
    }
    
    /**
     * Updates the name of a task identified by task ID.
     * 
     * @param taskId The ID of the task to update
     * @param name The new name for the task
     * @throws IllegalArgumentException if task ID is null, task is not found, or name is invalid
     */
    public void updateTaskName(String taskId, String name) {
        Task task = getTaskForUpdate(taskId);
        task.setName(name);
    }
    
    /**
     * Updates the description of a task identified by task ID.
     * 
     * @param taskId The ID of the task to update
     * @param description The new description for the task
     * @throws IllegalArgumentException if task ID is null, task is not found, or description is invalid
     */
    public void updateTaskDescription(String taskId, String description) {
        Task task = getTaskForUpdate(taskId);
        task.setDescription(description);
    }
    
    /**
     * Updates both name and description of a task identified by task ID.
     * 
     * @param taskId The ID of the task to update
     * @param name The new name for the task
     * @param description The new description for the task
     * @throws IllegalArgumentException if task ID is null, task is not found, or parameters are invalid
     */
    public void updateTask(String taskId, String name, String description) {
        Task task = getTaskForUpdate(taskId);
        task.setName(name);
        task.setDescription(description);
    }
    
    /**
     * Retrieves a task by its task ID.
     * 
     * @param taskId The ID of the task to retrieve
     * @return The Task object, or null if not found
     */
    public Task getTask(String taskId) {
        if (taskId == null) {
            return null;
        }
        return tasks.get(taskId);
    }
    
    /**
     * Helper method to get a task for update operations with proper validation.
     * 
     * @param taskId The ID of the task to retrieve for updating
     * @return The Task object
     * @throws IllegalArgumentException if task ID is null or task is not found
     */
    private Task getTaskForUpdate(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        
        Task existingTask = tasks.get(taskId);
        if (existingTask == null) {
            throw new IllegalArgumentException(
                String.format("Task with ID '%s' not found", taskId));
        }
        
        return existingTask;
    }
    
    /**
     * Checks if a task with the specified ID exists.
     * 
     * @param taskId The task ID to check
     * @return true if task exists, false otherwise
     */
    public boolean hasTask(String taskId) {
        if (taskId == null) {
            return false;
        }
        return tasks.containsKey(taskId);
    }
    
    /**
     * Gets the total number of tasks in the service.
     * 
     * @return The number of tasks
     */
    public int getTaskCount() {
        return tasks.size();
    }
    
    /**
     * Checks if the task service is empty.
     * 
     * @return true if no tasks exist, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
    
    /**
     * Gets all tasks as an unmodifiable collection.
     * 
     * @return Unmodifiable collection of all tasks
     */
    public Collection<Task> getAllTasks() {
        return Collections.unmodifiableCollection(tasks.values());
    }
    
    /**
     * Gets all task IDs as an unmodifiable collection.
     * 
     * @return Unmodifiable collection of all task IDs
     */
    public Collection<String> getAllTaskIds() {
        return Collections.unmodifiableSet(tasks.keySet());
    }
    
    /**
     * Searches for tasks by name (case-insensitive partial match).
     * 
     * @param nameQuery The name to search for
     * @return List of tasks matching the name query
     */
    public List<Task> searchTasksByName(String nameQuery) {
        if (nameQuery == null || nameQuery.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Task> results = new ArrayList<>();
        String lowerQuery = nameQuery.toLowerCase().trim();
        
        for (Task currentTask : tasks.values()) {
            if (currentTask.getName().toLowerCase().contains(lowerQuery)) {
                results.add(currentTask);
            }
        }
        
        return results;
    }
    
    /**
     * Searches for tasks by description (case-insensitive partial match).
     * 
     * @param descriptionQuery The description to search for
     * @return List of tasks matching the description query
     */
    public List<Task> searchTasksByDescription(String descriptionQuery) {
        if (descriptionQuery == null || descriptionQuery.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Task> results = new ArrayList<>();
        String lowerQuery = descriptionQuery.toLowerCase().trim();
        
        for (Task currentTask : tasks.values()) {
            if (currentTask.getDescription().toLowerCase().contains(lowerQuery)) {
                results.add(currentTask);
            }
        }
        
        return results;
    }
    
    /**
     * Clears all tasks from the service.
     * Use with caution as this operation cannot be undone.
     */
    public void clearAllTasks() {
        tasks.clear();
    }
    
    /**
     * Returns a string representation of the TaskService.
     * 
     * @return String representation showing the number of tasks
     */
    @Override
    public String toString() {
        return String.format("TaskService{taskCount=%d}", tasks.size());
    }
}
