package com.grandstrand.repository;

import com.grandstrand.domain.Task;
import java.util.*;

/**
 * Enhanced TaskRepository with HashMap name index for O(1) exact-match lookup.
 * Replaces the O(n) linear scan of the original in-memory implementation.
 */
public class IndexedTaskRepository implements TaskRepository {
    private final Map<String, Task> tasksById;
    private final Map<String, List<Task>> tasksByName;

    public IndexedTaskRepository() {
        this.tasksById = new HashMap<>();
        this.tasksByName = new HashMap<>();
    }

    @Override
    public Task save(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        
        // Remove from old name index if updating
        Task existing = tasksById.get(task.getTaskId());
        if (existing != null) {
            removeFromNameIndex(existing);
        }
        
        tasksById.put(task.getTaskId(), task);
        addToNameIndex(task);
        return task;
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return Optional.ofNullable(tasksById.get(taskId));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasksById.values());
    }

    /**
     * Finds tasks by exact name match using HashMap index.
     * Time complexity: O(1) average case.
     * Original linear scan was O(n).
     */
    public List<Task> findByName(String name) {
        List<Task> result = tasksByName.get(name);
        return result != null ? new ArrayList<>(result) : new ArrayList<>();
    }

    @Override
    public void deleteById(String taskId) {
        Task task = tasksById.remove(taskId);
        if (task != null) {
            removeFromNameIndex(task);
        }
    }

    @Override
    public boolean existsById(String taskId) {
        return taskId != null && tasksById.containsKey(taskId);
    }

    @Override
    public int count() {
        return tasksById.size();
    }

    @Override
    public void clear() {
        tasksById.clear();
        tasksByName.clear();
    }

    private void addToNameIndex(Task task) {
        tasksByName.computeIfAbsent(task.getName(), k -> new ArrayList<>()).add(task);
    }

    private void removeFromNameIndex(Task task) {
        List<Task> nameList = tasksByName.get(task.getName());
        if (nameList != null) {
            nameList.remove(task);
            if (nameList.isEmpty()) {
                tasksByName.remove(task.getName());
            }
        }
    }
}
