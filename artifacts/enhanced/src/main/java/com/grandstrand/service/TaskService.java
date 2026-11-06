package com.grandstrand.service;

import com.grandstrand.domain.Task;
import com.grandstrand.repository.TaskRepository;
import com.grandstrand.repository.IndexedTaskRepository;
import com.grandstrand.dao.TaskDaoSqlite;
import java.util.List;

/**
 * TaskService with dependency injection of TaskRepository.
 * Enhanced from original CS 320 version to use the Repository pattern.
 * 
 * Milestone Three enhancement: Uses IndexedTaskRepository for
 * O(1) name lookups instead of O(n) linear scans.
 */
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    public Task addTask(String taskId, String name, String description) {
        if (repository.existsById(taskId)) {
            throw new IllegalArgumentException("Task with ID '" + taskId + "' already exists");
        }
        Task task = new Task(taskId, name, description);
        return repository.save(task);
    }

    public Task getTask(String taskId) {
        return repository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task with ID '" + taskId + "' not found"));
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    /**
     * Finds tasks by exact name match.
     * Uses HashMap index for O(1) performance when repository supports it.
     */
    public List<Task> getTasksByName(String name) {
        if (repository instanceof IndexedTaskRepository) {
            return ((IndexedTaskRepository) repository).findByName(name);
        }
        if (repository instanceof TaskDaoSqlite) {
            return ((TaskDaoSqlite) repository).findByName(name);
        }
        // Fallback to linear scan for non-indexed repositories
        List<Task> result = new java.util.ArrayList<>();
        for (Task t : repository.findAll()) {
            if (t.getName().equals(name)) {
                result.add(t);
            }
        }
        return result;
    }

    public void deleteTask(String taskId) {
        if (!repository.existsById(taskId)) {
            throw new IllegalArgumentException("Task with ID '" + taskId + "' not found");
        }
        repository.deleteById(taskId);
    }

    public void updateTaskName(String taskId, String name) {
        Task task = getTask(taskId);
        task.setName(name);
        repository.save(task);
    }

    public void updateTaskDescription(String taskId, String description) {
        Task task = getTask(taskId);
        task.setDescription(description);
        repository.save(task);
    }

    public void updateTask(String taskId, String name, String description) {
        Task task = getTask(taskId);
        task.setName(name);
        task.setDescription(description);
        repository.save(task);
    }

    public int getTaskCount() {
        return repository.count();
    }

    public boolean hasTask(String taskId) {
        return repository.existsById(taskId);
    }

    public boolean isEmpty() {
        return repository.count() == 0;
    }

    public void clearAllTasks() {
        repository.clear();
    }
}
