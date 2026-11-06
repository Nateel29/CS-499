package com.grandstrand.repository;

import com.grandstrand.domain.Task;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Task persistence.
 */
public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(String taskId);
    List<Task> findAll();
    void deleteById(String taskId);
    boolean existsById(String taskId);
    int count();
    void clear();
}
