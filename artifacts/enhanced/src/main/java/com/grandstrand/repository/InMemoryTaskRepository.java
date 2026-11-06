package com.grandstrand.repository;

import com.grandstrand.domain.Task;
import java.util.*;

/**
 * In-memory implementation of TaskRepository using HashMap.
 */
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<String, Task> tasks = new HashMap<>();

    @Override
    public Task save(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteById(String taskId) {
        tasks.remove(taskId);
    }

    @Override
    public boolean existsById(String taskId) {
        return taskId != null && tasks.containsKey(taskId);
    }

    @Override
    public int count() {
        return tasks.size();
    }

    @Override
    public void clear() {
        tasks.clear();
    }
}
